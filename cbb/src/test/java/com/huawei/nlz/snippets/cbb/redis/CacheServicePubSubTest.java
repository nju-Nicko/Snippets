package com.huawei.nlz.snippets.cbb.redis;

import com.huawei.nlz.snippets.cbb.SpringJUnitContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheServicePubSubTest extends SpringJUnitContext {
    @Autowired
    private CacheService cacheService;

    @Test
    public void testPubSub(){
        String channel = "demoChannel";
        String msg = "hello!";

        // 发布消息到渠道
        cacheService.getRedisTemplate().convertAndSend(channel, msg);

        /*
         * 等待消息监听任务打印输出。
         */
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
            log.error("error occurred.");
        }
    }

    /**
     * 渠道消息订阅
     */
    @Component
    public static class MyRedisMessageListenerContainer extends RedisMessageListenerContainer {
        @Autowired
        private CacheService cacheService;

        @Override
        @Autowired
        public void setConnectionFactory(RedisConnectionFactory redisConnectionFactory){
            super.setConnectionFactory(redisConnectionFactory);
        }

        @PostConstruct
        public void init(){
            Map<MessageListener, Collection<? extends Topic>> listeners = new HashMap<>();
            listeners.put(new MessageListener() {
                @Override
                public void onMessage(Message message, byte[] pattern) {
                    byte[] body = message.getBody();
                    byte[] channel = message.getChannel();
                    // 通过ValueSerializer反序列化
                    log.info("message body is {}.", cacheService.getRedisTemplate().getValueSerializer().deserialize(body));
                    log.info("channel is {}.", cacheService.getRedisTemplate().getValueSerializer().deserialize(channel));
                }
            }, new ArrayList<ChannelTopic>() {
                {
                    add(new ChannelTopic("demoChannel"));
                }
            });
            super.setMessageListeners(listeners);
        }
    }
}
