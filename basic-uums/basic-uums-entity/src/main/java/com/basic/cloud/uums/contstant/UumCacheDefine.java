package com.basic.cloud.uums.contstant;

import com.basic.cloud.common.contstant.CacheDefine;

/**
 * @Author lanrenspace@163.com
 * @Description: uum 服务系统缓存定义
 **/
public class UumCacheDefine {

    /**
     * 资源URL KEY
     *
     * @return
     */
    public final static String RES_URL_KEY = CacheDefine.getKey("UUM:PLATFORM", "RES_URL");

    /**
     * 黑名单IP地址 KEY
     */
    public final static String BLACK_IPS_KEY = CacheDefine.getKey("UUM:PLATFORM", "BLACK_IPS");

    /**
     * 匿名接口信息 KEY
     */
    public final static String ANONYMOUS_INFO_KEY = CacheDefine.getKey("UUM:PLATFORM", "ANONYMOUS_INFO");
}
