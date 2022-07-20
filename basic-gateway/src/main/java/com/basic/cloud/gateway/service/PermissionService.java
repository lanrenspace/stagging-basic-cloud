package com.basic.cloud.gateway.service;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface PermissionService {

    /**
     * 用户鉴权
     * @param authentication
     * @param userId
     * @param url
     * @param method
     * @return
     */
    boolean permission(String authentication, String userId, String url, String method);
}
