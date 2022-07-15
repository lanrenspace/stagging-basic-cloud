package com.basic.cloud.common.base;

import java.util.Set;

/**
 * @Author lanrenspace@163.com
 * @Description: 租户业务数据表过滤提供器
 **/
@FunctionalInterface
public interface TenantTableFilterProvider {

    /**
     * 配置要过滤的tables
     *
     * @return
     */
    Set<String> configTable();
}
