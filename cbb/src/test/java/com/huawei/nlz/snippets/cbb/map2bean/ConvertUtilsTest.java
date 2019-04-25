package com.huawei.nlz.snippets.cbb.map2bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class ConvertUtilsTest {

    @Test
    public void testMap2Bean()
            throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        Map<String, Object> params = new HashMap<String, Object>(16) {
            {
                put("sessionId", UUID.randomUUID().toString());
                put("lastAccessTime", System.currentTimeMillis());
                CallId callId = new CallId("101", System.currentTimeMillis());
                put("callId", callId);
            }
        };

        Session session = ConvertUtils.map2Bean(params, Session.class);
        log.info("session is {}", session);
    }

    @Test
    public void testBean2Map() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Session session = new Session();
        session.setSessionId(UUID.randomUUID().toString());
        session.setLastAccessTime(System.currentTimeMillis());
        CallId callId = new CallId("101", System.currentTimeMillis());
        session.setCallId(callId);

        Map<String, Object> resultMap = ConvertUtils.bean2Map(session);
        log.info("result map is {}", resultMap);
    }

    @Data
    private static class Session {
        private String sessionId;
        private long lastAccessTime;
        private CallId callId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CallId {
        private String serverNO;
        private long timestamp;
    }

}
