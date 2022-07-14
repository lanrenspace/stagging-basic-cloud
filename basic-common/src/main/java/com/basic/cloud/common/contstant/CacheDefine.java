package com.basic.cloud.common.contstant;

import org.springframework.util.Assert;

/**
 * @Author lanrenspace@163.com
 * @Description: 缓存key结构定义
 **/
public interface CacheDefine {

    /**
     * 前缀
     */
    String PREFIX = "basic";
    String SP = ":";
    String DOT = ".";
    String U = "_";

    /**
     * 获取Key
     *
     * @param serviceName
     * @param key
     * @return
     */
    default String getKey(String serviceName, String key) {
        Assert.notNull(serviceName, "serviceName 不能为空!");
        Assert.notNull(key, "key 不能为空!");
        return PREFIX + SP + serviceName + SP + key;
    }
}
