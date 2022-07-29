package com.basic.cloud.common.bean;

import com.basic.cloud.common.boot.PlatformProperties;
import com.basic.cloud.common.exceptions.IdempotentNoLockException;
import com.basic.cloud.common.idempotent.IdempotentInfo;
import com.basic.cloud.common.idempotent.IdempotentManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class RedisIdempotentManager implements IdempotentManager {
    private final ValueOperations<String, String> valueOperations;

    private final PlatformProperties platformProperties;

    private final StringRedisTemplate stringRedisTemplate;

    private final String pid = ManagementFactory.getRuntimeMXBean().getName();

    public RedisIdempotentManager(StringRedisTemplate stringRedisTemplate,
                                  PlatformProperties platformProperties) {
        Assert.notNull(stringRedisTemplate, "stringRedisTemplate 不能为空");
        Assert.notNull(platformProperties, "platformProperties 不能为空");
        this.platformProperties = platformProperties;
        this.stringRedisTemplate = stringRedisTemplate;
        this.valueOperations = stringRedisTemplate.opsForValue();
    }

    @Override
    public boolean tryLock(IdempotentInfo idempotentInfo) {
        return Boolean.TRUE.equals(valueOperations
                .setIfAbsent(generatorKey(idempotentInfo.getKey()), generatorValue(),
                        idempotentInfo.getMaxLockMilli(), TimeUnit.MILLISECONDS));
    }

    @Override
    public Object handlerNoLock(IdempotentInfo idempotentInfo) {
        throw new IdempotentNoLockException("无法获取到锁,不能进行操作,稍后再试");
    }

    @Override
    public void complete(IdempotentInfo idempotentInfo, Object result) {
        stringRedisTemplate.delete(generatorKey(idempotentInfo.getKey()));
    }

    private String generatorKey(String key) {
        String keyPrefix = platformProperties.getIdem().getRedisKeyPrefix();
        if (StringUtils.isNotBlank(keyPrefix)) {
            key = keyPrefix + key;
        }
        return key;
    }

    private String generatorValue() {
        return pid + "_" + Thread.currentThread().getName();
    }
}
