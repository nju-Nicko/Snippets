package com.huawei.nlz.snippets.cbb;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CbbConfig.class, loader = AnnotationConfigContextLoader.class)
public class SpringJUnitContext {
}
