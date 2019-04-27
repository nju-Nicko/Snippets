package com.huawei.nlz.snippets.cbb.redis;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * CacheService
 */
public interface CacheService {

    /**
     * 存储HashKey
     *
     * @param key       主对象Key值
     * @param hashKey   主对象Hash属性Key
     * @param hashValue 属性Value
     */
    void putStrHash(String key, String hashKey, String hashValue);

    /**
     * Redis事务控制，获取锁
     *
     * @param key   事务锁的key
     * @param value 事务锁的value
     * @return boolean，成功获取锁返回true，否则返回false。
     */
    boolean setIfAbsent(String key, String value);

    /**
     * 设置值
     *
     * @param key
     * @param value 值
     */
    void setValue(String key, String value);

    /**
     * 根据Key值获取对应的缓存value
     *
     * @param key 缓存key值
     * @return 缓存value
     */
    String getValue(String key);

    /**
     * 将缓存的数据自动增加指定的值，redis原子操作
     *
     * @param key   缓存key
     * @param delta 需要变化的量
     * @return 变化后的值
     */
    Long increment(String key, long delta);

    /**
     * 判断指定的key是否包含hashKey
     *
     * @param key 缓存key
     * @param hk  对象的hashKey
     * @return 存在返回true，否则返回false
     */
    boolean hasHashKey(String key, String hk);

    /**
     * 判断缓存的key是否存在
     *
     * @param key 缓存key
     * @return 存在返回true，否则返回false
     */
    boolean hasKey(String key);

    /**
     * 获取HashKey的value
     *
     * @param key     缓存主Key
     * @param hashKey 缓存HashKey
     * @return 对应的value
     */
    String getStrHash(String key, String hashKey);

    /**
     * 批量添加多个HashKey
     *
     * @param key 缓存主Key
     * @param map K-V Map
     */
    void putStrHashAll(String key, Map<String, String> map);

    /**
     * 删除指定的HashKey
     *
     * @param key     缓存主Key
     * @param hashKey 缓存HashKey
     */
    void deleteStrHashKey(String key, String hashKey);

    /**
     * 以Map方式读取HashKey
     *
     * @param key 缓存主Key
     * @return 主key下的K-V Map
     */
    Map strHashEntries(String key);

    /**
     * 读取缓存中指定长度的Set
     *
     * @param key        缓存Key
     * @param startIndex 开始索引
     * @param endIndex   结束索引
     * @return 开始索引和结束索引之间的Set
     */
    Set<String> zSetRange(String key, long startIndex, long endIndex);

    /**
     * 读取指定Set缓存
     *
     * @param key 缓存Key
     * @return Set缓存
     */
    Set<String> zSetRange(String key);

    /**
     * 读取Set缓存的长度
     *
     * @param key 缓存key
     * @return Set长度
     */
    Long zSetSize(String key);

    /**
     * 向缓存的Set中添加一个元素
     *
     * @param key   缓存Key
     * @param value Set元素值
     * @param score Set元素的权重
     */
    void zSetAdd(String key, String value, Double score);

    /**
     * 删除缓存Set中指定范围内的数据
     *
     * @param key        缓存Key
     * @param startIndex 开始索引
     * @param endIndex   结束索引
     */
    void zSetRemoveRange(String key, long startIndex, long endIndex);

    /**
     * 删除Set缓存中的对象
     *
     * @param key     缓存Key
     * @param objects 需要删除的对象，可变参数
     */
    void zSetRemove(String key, Object... objects);

    /**
     * 增加Set缓存中的指定对象的权重，原子操作
     *
     * @param key   缓存key
     * @param value 缓存对象
     * @param delta 权重变量
     * @return 操作成功后的新权重
     */
    Double zSetIncrementScore(String key, String value, double delta);

    /**
     * 读取Set缓存对象权重
     *
     * @param key 缓存Key
     * @param hk  缓存对象
     * @return 对象权重
     */
    Double zSetScore(String key, Object hk);

    /**
     * 设置缓存中Key超时时间
     *
     * @param key      缓存Key
     * @param timeout  超时时间数值
     * @param timeUnit 超时时间单位
     */
    void setKeyExpire(String key, long timeout, TimeUnit timeUnit);

    /**
     * 删除指定的Key
     *
     * @param key String
     */
    void deleteKey(String key);

    /**
     * 泛型查询，查询缓存的对象
     *
     * @param key 缓存Key
     * @param clz 缓存对象类型
     * @return 缓存的对象
     */
    <T> T queryEntity(String key, Class clz);

    /**
     * 缓存指定类型的对象
     *
     * @param key 缓存Key
     * @param t   对象值
     */
    <T> void saveEntity(String key, T t);

    /**
     * 获取Redis服务器时间
     *
     * @return Redis服务器当前时间戳
     */
    long globalNow();

}