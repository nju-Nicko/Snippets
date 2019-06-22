package com.huawei.nlz.snippets.cbb.longpolling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 长轮询服务端的一种实现
 */
@Slf4j
public class ChatFacade {

    /**
     * 长轮询超时时间25秒
     */
    private static final int LONG_POLLING_TIMEOUT = 25000;

    @SuppressWarnings("ConstantConditions")
    public List<String> getMessage(String uid) {
        long requestTime = System.currentTimeMillis();

        List<String> resultMsgs = new ArrayList<>();

        while (System.currentTimeMillis() - requestTime <= LONG_POLLING_TIMEOUT) {
            List<String> msgs = null;  // 调用其他服务获取消息，这里省略掉了。

            if (!CollectionUtils.isEmpty(msgs)) {
                resultMsgs.addAll(msgs);
                break;
            }

            try {
                TimeUnit.SECONDS.sleep(1);  // 休眠1秒后继续轮询
            } catch (Exception e) {
                log.error("error occurred, interrupted exception.", e);
            }
        }

        return resultMsgs;
    }

}
