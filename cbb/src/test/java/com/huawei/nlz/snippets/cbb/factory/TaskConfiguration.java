package com.huawei.nlz.snippets.cbb.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class TaskConfiguration extends AbstractConfiguration {

    private TaskConfiguration() {

    }

    @Override
    public String getRegistrationKey() {
        return "task";
    }

    @Override
    public void configuration() {
        log.info("task configuration!");
    }
}
