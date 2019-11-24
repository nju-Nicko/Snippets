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
        configurationFactory.getConfiguration("case").configuration();
        configurationFactory.getConfiguration("task").configuration();
    }
}
