package com.huawei.nlz.snippets.cbb.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CaseConfiguration extends AbstractConfiguration {

    private CaseConfiguration() {

    }

    @Override
    public String getRegistrationKey() {
        return "case";
    }

    @Override
    public void configuration() {
        log.info("case configuration!");
    }
}
