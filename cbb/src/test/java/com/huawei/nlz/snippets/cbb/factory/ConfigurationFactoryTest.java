package com.huawei.nlz.snippets.cbb.factory;

import com.huawei.nlz.snippets.cbb.CbbConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CbbConfig.class, loader = AnnotationConfigContextLoader.class)
@Slf4j
public class ConfigurationFactoryTest {

    @Autowired
    private ConfigurationFactory configurationFactory;

    @Test
    public void testFactory() {
        log.info("case configuration is: {}." + configurationFactory.getConfiguration("case").getConfigDetail());
        log.info("task configuration is: {}." + configurationFactory.getConfiguration("task").getConfigDetail());
    }

}
