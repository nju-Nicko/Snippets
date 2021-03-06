package com.huawei.nlz.snippets.cbb.redis;

import com.alibaba.fastjson.JSON;
import com.huawei.nlz.snippets.cbb.SpringJUnitContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
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
        session.setLastAccessTime(System.currentTimeMillis());
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

        cacheService.deleteKey("ids:msg");
        log.info(cacheService.getRedisTemplate().opsForValue().increment("ids:msg") + "");  //1
        log.info(cacheService.getRedisTemplate().opsForValue().increment("ids:msg") + "");  //2
        log.info(cacheService.getRedisTemplate().opsForValue().increment("ids:msg", 7) + "");  //9

        cacheService.deleteKey("session:15950570398@wechat");
        Session session1 = cacheService.queryEntity("session:15950570398@wechat", Session.class);
        // 对象不为空，但是字段都是null，数值型的字段值是0
        log.info("session1 is {}.", session1);

        cacheService.getRedisTemplate().execute(new SessionCallback<Boolean>() {
            @Override
            public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.watch((K) "SESSION:123456789");
                // 要放到事务外面来查询
                long lastAccessTime = Long.parseLong(cacheService.getStrHash("SESSION:123456789", "lastAccessTime"));
                operations.multi();
                if (System.currentTimeMillis() - lastAccessTime > 5 * 60 * 1000) {
                    cacheService.deleteKey("SESSION:123456789");
                }
                List<Object> result = operations.exec();
                return null;
            }
        });
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Session {
        private String sessionId;
        private String userName;
        private String userAccount;
        private long lastAccessTime;
    }
}
