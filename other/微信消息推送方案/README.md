任务到来时添加到任务队列。同时维护一个线程池，核心池大小1k，专门用来执行任务，微信消息push任务，称之为执行池。  
单线程迭代任务队列，取一个任务，如果执行池有可用线程，则提交给执行池去执行；否则等待一段时间，再检查执行池是否有可用线程。任务队列迭代完后，从头开始重新迭代，继续上述处理。  
用户断开会话或者超时断开时，从任务队列里移除该用户对应的任务。  
当一个任务已被活动线程处理时，无需再将其提交给执行池执行，因此可以在任务上增加一个状态字段，记录任务是处于执行中还是未处于执行中。当任务队列迭代线程取到任务后，先判断它的状态，如果是已在执行中，则跳过，扫描下一个任务项；如果未处于执行中，则标记为执行中，然后提交给执行池。执行池里的Runnable执行完任务后将它的状态再改回非运行中状态。  
编码:
````
/**
 * 微信消息push任务。
 *
 * @version 2019-05-22
 * @since 2019-05-22
 */
@Component
@Log4j2
public class WechatResponseTask {
    private static final int WECHAT_RESPONSE_TASK_CORE_POOL_SIZE = SystemPropertyUtils.getIntProperty(
        "ccmessaging.social.wechat.responseTaskCorePoolSize", 1000);

    private static final String WECHAT_ADAPTER_ENDPOINT = "rest://rest/wechatadapter/v1/message" ;

    private static final String AUTH_TOKEN_NAME = "X-Access-Token";

    private static final String MICRO_SERVICE_NAME = "ChatSocialConnectorService";

    private static final int NUMBER_2 = 2;

    private static final int NUMBER_5 = 5;

    private static final int POSITIVE_ONE = 1;

    private static final int THREAD_SLEEP_INTERVAL = 500;

    private static final int BLOCKING_QUEUE_TIMEOUT_WAIT_SECONDS = 30;

    private BlockingQueue<WechatResponseTask.TaskDetail> tasks = new ArrayBlockingQueue<>(
        WECHAT_RESPONSE_TASK_CORE_POOL_SIZE * NUMBER_2);

    /**
     * 登记任务项信息。
     */
    private Map<String, WechatResponseTask.TaskDetail> taskRegistryMap = BaseCollectionUtils.newConcurrentHashMap();

    private ThreadPoolExecutor executionPool = new ThreadPoolExecutor(
        WECHAT_RESPONSE_TASK_CORE_POOL_SIZE,
        WECHAT_RESPONSE_TASK_CORE_POOL_SIZE,
        0L,
        TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<>()
    );

    /**
     * SIA认证服务
     */
    @Autowired
    private ApiAuthService apiAuthService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private InteractiveService interactiveService;

    @Autowired
    private CCUCSEventProcessorDelegate ccucsEventProcessorDelegate;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private MessageBuffer messageBuffer;

    @Autowired
    private ConfigurationService configurationService;

    /**
     * PostConstruct Initialization
     */
    @PostConstruct
    public void wechatResponseTaskInit() {
        Thread executionThread = new Thread(() -> {
            // 单线程轮询微信消息push任务列表，提交给线程池执行。
            while (true) {
                try {
                    WechatResponseTask.TaskDetail taskDetail = tasks.take();

                    // 任务已结束则跳过，该任务项不用再放回任务队列。
                    if ("1".equals(taskDetail.getStatus())) {
                        continue;
                    }

                    // 轮询执行池至其处于非满
                    boolean isCorePoolFull = executionPool.getActiveCount() >= WECHAT_RESPONSE_TASK_CORE_POOL_SIZE;
                    while (isCorePoolFull) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(THREAD_SLEEP_INTERVAL);
                        } catch (Exception e) {
                            log.error("error occurred while thread sleep.", e);
                        }
                        isCorePoolFull = executionPool.getActiveCount() >= WECHAT_RESPONSE_TASK_CORE_POOL_SIZE;
                    }

                    // 执行池非满时提交一个任务给其执行
                    Runnable runnable = new WechatResponseRunnable(taskDetail);
                    executionPool.execute(runnable);
                } catch (Exception e) {
                    log.error("error occurred while executing wechat response task.", e);
                }
            }
        }, "wechat-msg-push-tasklist-looper");
        executionThread.setDaemon(true);
        executionThread.start();
    }

    private String getToken() {
        return apiAuthService.getToken(MICRO_SERVICE_NAME);
    }

    /**
     * 增加微信消息push任务
     *
     * @param userId 用户ID
     * @param taskDetail 任务详情
     * @return true 添加任务成功; false 添加任务失败
     */
    public boolean addTask(String userId, WechatResponseTask.TaskDetail taskDetail) {
        taskRegistryMap.put(userId, taskDetail);
        boolean isOfferSuccess;
        taskDetail.setUserId(userId);
        try {
            isOfferSuccess = tasks.offer(taskDetail, BLOCKING_QUEUE_TIMEOUT_WAIT_SECONDS, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("error occurred, interrupted exception.", e);
            isOfferSuccess = false;
        }
        return isOfferSuccess;
    }

    /**
     * 移除微信消息push任务
     *
     * @param userId 用户ID
     */
    public void removeTask(String userId) {
        WechatResponseTask.TaskDetail taskDetail = taskRegistryMap.get(userId);

        if (null != taskDetail) {
            boolean isRemoveSuccess = tasks.remove(taskDetail);

            /* 当移除微信push任务项的时候可能存在两种场景：
             *  1. 任务项在阻塞队列中，这时候直接移除阻塞队列里的该项和任务注册中心的该项即可;
             *  2. 任务项被take出来，这时候在任务注册中心里标记该项为已完成状态，对应的线程在释放该项时不释放到任务队列。
             */
            if (isRemoveSuccess) {
                taskRegistryMap.remove(userId);
            } else {
                // 标记任务
                taskDetail.setStatus("1");
            }
        }
    }

    /**
     * 任务详情
     *
     * @author n00383641
     * @since 2019-05-22
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskDetail {
        private String userId;

        private ChannelConfig channelConfigMap;

        /**
         * 状态，0表示未结束，1表示已结束
         */
        private String status = "0";

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (other instanceof WechatResponseTask.TaskDetail) {
                WechatResponseTask.TaskDetail otherTaskDetail = (WechatResponseTask.TaskDetail) other;
                // userId相等即视为是相同的任务项。
                if (otherTaskDetail.getUserId().equals(this.userId)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public int hashCode() {
            return (userId == null) ? 0 : userId.hashCode();
        }
    }

    /**
     * 微信消息push Runnable
     *
     * @author n00383641
     * @since 2019-05-22
     */
    public class WechatResponseRunnable implements Runnable {
        private WechatResponseTask.TaskDetail taskDetail;

        /**
         * Constructor
         *
         * @param taskDetail 任务详情
         */
        public WechatResponseRunnable(WechatResponseTask.TaskDetail taskDetail) {
            this.taskDetail = taskDetail;
        }

        @Override
        public void run() {
            String userId = this.taskDetail.getUserId();
            String channelId = this.taskDetail.getChannelConfigMap().getId();

            if (log.isDebugEnabled()) {
                log.debug("start to response to wechat user {}", userId);
            }

            List<DownlinkMessage> unreadDms = getUnreadMessagesHandleException(userId, channelId);

            List<DownlinkMessage> agentDms = BaseCollectionUtils.newArrayList();
            Session session = querySessionHandleException(userId, channelId);
            if ((null != session) && session.getLastAccessTime() > 0) {
                final String ccucsAddr = session.getCcucsAddr();
                Map<String, String> callIdMap = new HashMap<>(NUMBER_5);
                if (!ChatStringUtils.isTrimEmpty(session.getDsn())) {
                    callIdMap.put("dsn", session.getDsn());
                }
                if (!ChatStringUtils.isTrimEmpty(session.getServer())) {
                    callIdMap.put("server", session.getServer());
                }
                if (!ChatStringUtils.isTrimEmpty(session.getHandle())) {
                    callIdMap.put("handle", session.getHandle());
                }
                if (!ChatStringUtils.isTrimEmpty(session.getTime())) {
                    callIdMap.put("time", session.getTime());
                }
                if (!CommonUtil.isEmpty(callIdMap) && !ChatStringUtils.isTrimEmpty(ccucsAddr)) {
                    GetCcucsMessageResponse getCcucsMessageResponse = getCcucsMessageHandleException(
                        ccucsAddr, channelId, userId, callIdMap);
                    Optional.ofNullable(getCcucsMessageResponse).ifPresent(resp -> {
                        if (!CommonUtil.isEmpty(resp.getEvents())) {
                            List<BaseCcucsEvent> events = resp.getEvents();
                            // transform events to downlink messages
                            for (BaseCcucsEvent event : events) {
                                agentDms.addAll(processCcucsEventHandleException(event, userId,Channel.WECHAT.getValue(), channelId));
                            }
                        }
                    });
                }
            }

            List<DownlinkMessage> resultDms = BaseCollectionUtils.newArrayList();
            resultDms.addAll(unreadDms);
            resultDms.addAll(agentDms);

            if (log.isDebugEnabled()) {
                log.debug("wechat push task result dms of user {} is {}", userId, JSON.toJSONString(resultDms));
            }

            if (!CommonUtil.isEmpty(resultDms)) {
                // 对下行消息按时间进行排序
                resultDms.sort((o1, o2) -> {
                    long delta = o1.getTimestamp() - o2.getTimestamp();
                    if (delta == 0) {
                        return 0;
                    }
                    return (delta < 0) ? -POSITIVE_ONE : POSITIVE_ONE;
                });

                try {
                    String siaToken = getToken();
                    Map<String, Object> headerParams = BaseCollectionUtils.newHashMap();
                    headerParams.put(AUTH_TOKEN_NAME, siaToken);
                    headerParams.put("Accept-Language", "zh-CN");
                    RestUtils.doPost(WECHAT_ADAPTER_ENDPOINT, resultDms, null, null, headerParams, JSONObject.class);
                    messageBuffer.downlinkmessageBufferProcess(resultDms);
                } catch (Exception e) {
                    log.error("error occurred when response to wechat.", e);
                }
            }

            try {
                if (taskDetail.getStatus().equals("1")) {
                    // 任务已结束，不释放到任务队列
                    taskRegistryMap.remove(taskDetail.getUserId());
                } else {
                    // 释放任务到任务队列
                    tasks.put(taskDetail);
                }
            } catch (Exception e) {
                log.error("error occurred while release wechat response task to task queue.", e);
            }
        }

        private List<DownlinkMessage> getUnreadMessagesHandleException(String userId, String channelId) {
            List<DownlinkMessage> unreadDms = BaseCollectionUtils.newArrayList();
            try {
                unreadDms = messageService.getUnreadMessages(Channel.WECHAT.getValue(), userId, channelId);
            } catch (Exception e) {
                log.error("error occurred when get unread messages for wechat user {}.", userId, e);
            }
            return unreadDms;
        }

        private Session querySessionHandleException(String userId, String channelId) {
            String tenantId = configurationService.getTenantSpaceIdById(channelId);
            String sessionKey = SessionServiceUtils.generateSessionKey(channelId, userId, tenantId);
            Session session;
            try {
                session = cacheService.queryEntity(sessionKey, Session.class);
            } catch (Exception e) {
                session = null;
                log.error("error occurred while query session for wechat user {}.", userId);
            }
            return session;
        }

        private GetCcucsMessageResponse getCcucsMessageHandleException(String ccucsAddr, String channelId,
                                                                       String userId, Map<String, String> callId) {
            GetCcucsMessageResponse getCcucsMessageResponse = null;
            try {
                getCcucsMessageResponse = interactiveService.getCcucsMessage(
                    ccucsAddr, channelId, userId, callId);
            } catch (Exception e) {
                log.error("error occurred when getCCUCSMessage for wechat user {}.", userId, e);
            }
            return getCcucsMessageResponse;
        }

        private List<DownlinkMessage> processCcucsEventHandleException(BaseCcucsEvent event, String userId, String channel, String channelId) {
            List<DownlinkMessage> dms = BaseCollectionUtils.newArrayList();
            try {
                dms.addAll(ccucsEventProcessorDelegate.process(event, channel, userId, channelId));
            } catch (Exception e) {
                log.error("error occurred while process ccucs event for wechat user {}.", userId);
            }
            return dms;
        }
    }
}
````