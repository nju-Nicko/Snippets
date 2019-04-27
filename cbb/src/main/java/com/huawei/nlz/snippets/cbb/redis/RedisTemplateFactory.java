package com.huawei.nlz.snippets.cbb.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Slf4j
public class RedisTemplateFactory {

    @Autowired
    private RedisConnectionFactory factory;

    /**
     * 构造RedisTemplate对象
     *
     * @return RedisTemplate对象
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }

    /**
     * jedis Redis连接工厂
     */
    @Component
    public static class MyRedisConnectionFactory extends JedisConnectionFactory {

        @Value("${redis.ip}")
        public void setIP(String redisIP) {
            super.setHostName(redisIP);
        }

        @Value("${redis.port}")
        public void setPort(int port) {
            super.setPort(port);
        }

        @Value("${redis.password:}")
        public void setPassword(String password) {
            if (!StringUtils.isEmpty(password)) {
                super.setPassword(password);
            }
        }

        @Value("${redis.usePool}")
        public void setUsePool(boolean usePool) {
            super.setUsePool(usePool);
        }

        @Value("${redis.database}")
        public void setDatabase(int database) {
            super.setDatabase(database);
        }

        @Autowired
        @Override
        public void setPoolConfig(JedisPoolConfig poolConfig) {
            super.setPoolConfig(poolConfig);
        }

    }

    /**
     * 连接池配置类
     */
    @Component
    public static class MyRedisPoolConfig extends JedisPoolConfig {

        @Value("${redis.pool.maxTotal}")
        public void setMaxTotal(int maxTotal) {
            super.setMaxTotal(maxTotal);
        }

        @Value("${redis.pool.maxIdle}")
        public void setMaxIdle(int maxIdle) {
            super.setMaxIdle(maxIdle);
        }

        @Value("${redis.pool.minIdle}")
        public void setMinIdle(int minIdle) {
            super.setMinIdle(minIdle);
        }

        @Value("${redis.pool.maxWaitMillis}")
        public void setMaxWaitMillis(long maxWaitMillis) {
            super.setMaxWaitMillis(maxWaitMillis);
        }

        @Value("${redis.pool.minEvictableIdleTimeMillis}")
        public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
            super.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }

        @Value("${redis.pool.numTestsPerEvictionRun}")
        public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
            super.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        }

        @Value("${redis.pool.timeBetweenEvictionRunsMillis}")
        public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
            super.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }

    }

}