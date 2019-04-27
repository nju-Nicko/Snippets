package com.huawei.nlz.snippets.cbb.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void putStrHash(String key, String hashKey, String hashValue) {
        if (StringUtils.isEmpty(hashKey) || hashValue == null) {
            log.info("hash key or hash value is null !");
            return;
        }
        redisTemplate.boundHashOps(key).put(hashKey, hashValue);
    }

    @Override
    public boolean setIfAbsent(@NonNull String key, @NonNull String value) {
        return redisTemplate.boundValueOps(key).setIfAbsent(value);
    }

    @Override
    public void setValue(String key, String value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public String getValue(String key) {
        if (StringUtils.isEmpty(key)) {
            return "0";
        }
        String value = redisTemplate.boundValueOps(key).get();
        return StringUtils.isEmpty(value) ? "0" : value;
    }

    @Override
    public Long increment(String key, long delta) {
        return redisTemplate.boundValueOps(key).increment(delta);
    }

    @Override
    public boolean hasHashKey(String key, String hk) {
        return redisTemplate.boundHashOps(key).hasKey(hk);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public String getStrHash(String key, String hashKey) {
        return (String) redisTemplate.boundHashOps(key).get(hashKey);
    }

    @Override
    public void putStrHashAll(String key, Map<String, String> map) {
        redisTemplate.boundHashOps(key).putAll(map);
    }

    @Override
    public void deleteStrHashKey(String key, String hashKey) {
        redisTemplate.boundHashOps(key).delete(hashKey);
    }

    @Override
    public Map strHashEntries(String key) {
        if (StringUtils.isEmpty(key)) {
            return Collections.EMPTY_MAP;
        }
        return redisTemplate.boundHashOps(key).entries();
    }

    @Override
    public Set<String> zSetRange(String key, long startIndex, long endIndex) {
        if (key == null) {

            return Collections.EMPTY_SET;
        }

        return redisTemplate.boundZSetOps(key).range(startIndex, endIndex);
    }

    @Override
    public Set<String> zSetRange(String key) {

        return zSetRange(key, 0, Long.MAX_VALUE);
    }

    @Override
    public Long zSetSize(String key) {
        return StringUtils.isEmpty(key) ? Long.valueOf(0) : redisTemplate.boundZSetOps(key).size();
    }

    @Override
    public void zSetAdd(String key, String value, Double score) {
        redisTemplate.boundZSetOps(key).add(value, score);
    }

    @Override
    public void zSetRemoveRange(String key, long startIndex, long endIndex) {
        redisTemplate.boundZSetOps(key).removeRange(startIndex, endIndex);
    }

    @Override
    public void zSetRemove(String key, Object... objects) {
        redisTemplate.boundZSetOps(key).remove(objects);
    }

    @Override
    public Double zSetIncrementScore(String key, String value, double delta) {
        return redisTemplate.boundZSetOps(key).incrementScore(value, delta);
    }

    @Override
    public Double zSetScore(String key, Object hk) {
        return redisTemplate.boundZSetOps(key).score(hk);
    }

    @Override
    public void setKeyExpire(String key, long timeout, TimeUnit timeUnit) {
        if (redisTemplate.getExpire(key) == -1) {
            redisTemplate.expire(key, timeout, timeUnit);
        }
    }

    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public <T> T queryEntity(String key, Class clz) {
        T t = null;
        try {
            BeanUtilsHashMapper mapper = new BeanUtilsHashMapper(clz);
            Map<String, String> entries = strHashEntries(key);
            t = (T) mapper.fromHash(entries);
        } catch (Exception e) {
            log.error("Failed to get class:{} entity from cache with key:{}.", clz.getName(), key, e);
        }
        return t;
    }

    @Override
    public <T> void saveEntity(String key, T t) {
        try {
            BeanUtilsHashMapper<T> mapper = new BeanUtilsHashMapper(t.getClass());
            Map<String, String> map = mapper.toHash(t);
            putStrHashAll(key, map);
        } catch (Exception e) {
            log.error("Failed to store t:{} to redis with key:{}.", t, key, e);
        }
    }

    @Override
    public long globalNow() {
        long now;
        RedisConnection connection = null;
        try {
            connection = redisTemplate.getConnectionFactory().getConnection();
            now = Optional.ofNullable(connection).map(conn -> conn.time()).orElse(System.currentTimeMillis());
        } catch (Exception e) {
            now = System.currentTimeMillis();
            log.error("Failed to get currentTime for Redis.", e);
        } finally { // 释放连接到链接池
            RedisConnectionUtils.releaseConnection(connection, redisTemplate.getConnectionFactory());
        }
        return now;
    }

}

