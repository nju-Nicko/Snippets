package com.huawei.nlz.snippets.cbb.asynctask;

import com.huawei.nlz.snippets.cbb.SpringJUnitContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AsyncTaskTest extends SpringJUnitContext {

    @Autowired
    private BizThreadPool bizThreadPool;

    @Test
    public void testAsyncTask() {
        BizRunnable bizRunnable = new BizRunnable("1", "100", "200");
        bizThreadPool.execute(bizRunnable);
    }

}