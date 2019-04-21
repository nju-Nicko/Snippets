package com.huawei.nlz.snippets.cbb.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CaseConfiguration extends AbstractConfiguration {

    private CaseConfiguration() {

    }

    /**
     * Post construct initialization
     */
    @PostConstruct
    public void caseConfigurationInit() {
        configDetail = "this is case configuration";
    }

    @Override
    public String getRegistrationKey() {
        return "case";
    }

}
