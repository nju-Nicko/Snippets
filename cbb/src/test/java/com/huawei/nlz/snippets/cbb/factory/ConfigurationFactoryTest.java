package com.huawei.nlz.snippets.cbb.factory;

import com.huawei.nlz.snippets.cbb.SpringJUnitContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ConfigurationFactoryTest extends SpringJUnitContext {

    @Autowired
    private ConfigurationFactory configurationFactory;

    @Test
    public void testFactory() {
        log.info("case configuration is: {}." + configurationFactory.getConfiguration("case").getConfigDetail());
        log.info("task configuration is: {}." + configurationFactory.getConfiguration("task").getConfigDetail());
    }

}
