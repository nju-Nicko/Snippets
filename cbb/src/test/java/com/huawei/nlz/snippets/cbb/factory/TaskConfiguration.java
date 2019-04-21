package com.huawei.nlz.snippets.cbb.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class TaskConfiguration extends AbstractConfiguration {

    private TaskConfiguration() {

    }

    /**
     * Post construct initialization
     */
    @PostConstruct
    public void taskConfigurationInit() {
        configDetail = "this is task configuration";
    }

    @Override
    public String getRegistrationKey() {
        return "task";
    }

}
