package com.huawei.nlz.snippets.cbb.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置工厂
 */
@Component
@Slf4j
public class ConfigurationFactory implements Registry {

    private static final int REGISTRY_INIT_CAPACITY = 16;

    private Map<String, AbstractConfiguration> registry = new ConcurrentHashMap<>(REGISTRY_INIT_CAPACITY);

    /**
     * 隐藏构造函数使得只能通过Spring上下文来获取
     */
    private ConfigurationFactory() {

    }

    @Override
    public void register(String key, Object object) {
        registry.put(key, (AbstractConfiguration) object);
    }

    /**
     * 根据key获取配置对象
     *
     * @param key key
     * @return Configuration对象
     */
    public AbstractConfiguration getConfiguration(String key) {
        return registry.get(key);
    }
}
