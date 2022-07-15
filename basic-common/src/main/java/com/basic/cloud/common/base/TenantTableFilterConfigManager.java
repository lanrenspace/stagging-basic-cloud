package com.basic.cloud.common.base;

import java.util.Set;

/**
 * @Author lanrenspace@163.com
 * @Description: 租户业务数据过滤管理器
 **/
@FunctionalInterface
public interface TenantTableFilterConfigManager {

    /**
     * 获取所有配置的业务数据表
     *
     * @return
     */
    Set<String> config();
}
