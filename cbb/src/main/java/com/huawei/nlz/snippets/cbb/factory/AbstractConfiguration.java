package com.huawei.nlz.snippets.cbb.factory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Slf4j
@Data
public abstract class AbstractConfiguration implements Registerable {

    /**
     * 配置详情
     */
    protected String configDetail;

    @Autowired
    private ConfigurationFactory configurationFactory;

    /**
     * Post construct initialization
     */
    @PostConstruct
    public void abstractConfigurationInit(){
        configurationFactory.register(getRegistrationKey(), this);
    }

}
