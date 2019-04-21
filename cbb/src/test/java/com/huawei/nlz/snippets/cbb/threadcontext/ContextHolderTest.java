package com.huawei.nlz.snippets.cbb.threadcontext;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ContextHolderTest {

    @Test
    public void testThreadContext() {
        ContextHolder.Token loginToken = new ContextHolder.Token();
        loginToken.setUserId("1578907654");
        loginToken.setUserAccount("niluzhang");

        ContextHolder.setLoginToken(loginToken);

        log.info("user account of thread {} is {}.", Thread.currentThread().getName(), ContextHolder.getUserAccount());

        new Thread(() -> {
            ContextHolder.Token loginToken2 = new ContextHolder.Token();
            loginToken2.setUserId("1789657810");
            loginToken2.setUserAccount("nicko");

            ContextHolder.setLoginToken(loginToken2);

            log.info("user account of thread {} is {}.", Thread.currentThread().getName(), ContextHolder.getUserAccount());
        }).start();
    }

}
