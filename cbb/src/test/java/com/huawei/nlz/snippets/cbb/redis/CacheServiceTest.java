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
