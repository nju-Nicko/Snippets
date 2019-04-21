package com.huawei.nlz.snippets.cbb.factory;

/**
 * Registry
 */
public interface Registry {

    /**
     * 注册对象到注册中心。
     *
     * @param key    注册key
     * @param object 对象
     */
    void register(String key, Object object);
}
