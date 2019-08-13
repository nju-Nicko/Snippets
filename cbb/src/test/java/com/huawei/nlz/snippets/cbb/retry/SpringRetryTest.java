package com.huawei.nlz.snippets.cbb.retry;

import com.alibaba.fastjson.JSON;
import com.huawei.nlz.snippets.cbb.SpringJUnitContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SpringRetryTest extends SpringJUnitContext {

    @Autowired
    private BizLogic bizLogic;

    @Test
    public void testRpcWithRetry() {
        log.info("客户端rpc结果: {}", JSON.toJSONString(bizLogic.rpc()));
    }

}
