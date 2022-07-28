package com.basic.cloud.common.boot;

import com.basic.cloud.common.bean.RedisIdempotentManager;
import com.basic.cloud.common.idempotent.IdempotentManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@ConditionalOnBean(IdempotentConfig.class)
public class RedisIdempotentConfig {

    private final StringRedisTemplate redisTemplate;

    private final PlatformProperties platformProperties;

    public RedisIdempotentConfig(StringRedisTemplate redisTemplate, PlatformProperties platformProperties) {
        this.redisTemplate = redisTemplate;
        this.platformProperties = platformProperties;
    }

    @Bean
    public IdempotentManager redisIdempotentManager() {
        return new RedisIdempotentManager(redisTemplate, platformProperties);
    }
}
