package com.huawei.nlz.snippets.cbb.asynctask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BizThreadPool {

    private ThreadPoolExecutor executor;

    @Value("${biz.threadpool.size:4}")
    private int threadPoolSize;

    private static final int KEEP_ALIVE_TIME = 5;

    /**
     * PostConstruct initialization.
     */
    @PostConstruct
    public void init() {
        this.executor = new ThreadPoolExecutor(threadPoolSize / 2, threadPoolSize, KEEP_ALIVE_TIME, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(threadPoolSize / 2), new BizThreadFactory(), new BizRejectedExecutionHandler());
    }

    /**
     * 提交任务
     *
     * @param runnable 任务
     */
    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }

    /**
     * 更新后台任务
     *
     * @param backgroundTaskId     后台任务ID
     * @param backgroundTaskStatus 后台任务状态
     * @param desc                 状态描述
     */
    private void updateBackgroundTask(String backgroundTaskId, String backgroundTaskStatus, String desc) {
        //更新数据库里该任务对应的记录的status等
    }

    /**
     * 业务线程工厂。
     */
    class BizThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new BizUncaughtExceptionHandler());
            return thread;
        }

    }

    /**
     * 拒绝任务时的策略。
     */
    class BizRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            BizRunnable br = (BizRunnable) r;
            updateBackgroundTask(br.getBackgroundTaskId(), Constants.BIZ_ERROR, Constants.SYSTEM_BUSY);
        }

    }

    /**
     * 线程内异常处理。
     */
    class BizUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("error occurred in thread {}.", t.getName(), e);
            }
            String backgroundTaskId = t.getName().substring(Constants.THREAD_NAME_PREFIX.length());
            if (StringUtils.hasLength(backgroundTaskId)) {
                updateBackgroundTask(backgroundTaskId, Constants.BIZ_ERROR, Constants.UNKNOWN_ERROR);
            }
        }
    }
}
