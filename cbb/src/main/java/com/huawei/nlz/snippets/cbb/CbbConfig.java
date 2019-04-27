package com.huawei.nlz.snippets.cbb;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:/conf/snippets.conf.properties")
public class CbbConfig {
}
