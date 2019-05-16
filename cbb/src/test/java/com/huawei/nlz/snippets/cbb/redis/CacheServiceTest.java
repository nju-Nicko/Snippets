package com.huawei.nlz.snippets.cbb.redis;

import com.alibaba.fastjson.JSON;
import com.huawei.nlz.snippets.cbb.SpringJUnitContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheServiceTest extends SpringJUnitContext {

    @Autowired
    private CacheService cacheService;

    @Test
    public void testCache() {
        cacheService.setValue("name", "nlz");
        log.info("name is {}", cacheService.getValue("name"));

        Session session = new Session();
        session.setSessionId(UUID.randomUUID().toString());
        session.setUserAccount("123456789");
        session.setUserName("niluzhang");
        cacheService.saveEntity("SESSION:123456789", session);
        log.info("niluzhang's session is {}",
                JSON.toJSONString(cacheService.queryEntity("SESSION:123456789", Session.class)));

        // redis key名和value都可以包含空格
        cacheService.deleteKey("wechat 123456");
        log.info("setIfAbsent mykey: {}", cacheService.setIfAbsent("wechat 123456", "val"));  //true
        log.info("setIfAbsent mykey: {}", cacheService.setIfAbsent("wechat 123456", "val"));  //false

        // key已存在，但是没有设置失效时间，getExpire返回-1
        log.info(cacheService.getRedisTemplate().getExpire("wechat 123456") + "");
        // key不存在，getExpire返回-2
        log.info(cacheService.getRedisTemplate().getExpire("token") + "");
        cacheService.setValue("token", "token");
        cacheService.setKeyExpire("token", 10, TimeUnit.SECONDS);
        // 返回10，可见getExpire默认以秒为单位
        log.info(cacheService.getRedisTemplate().getExpire("token") + "");
        // 剩余存活时间的毫秒数
        log.info(cacheService.getRedisTemplate().getExpire("token", TimeUnit.MILLISECONDS) + "");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Session {
        private String sessionId;
        private String userName;
        private String userAccount;
    }
}
