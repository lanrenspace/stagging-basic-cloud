package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.uums.entity.ResourceAuthority;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface ResourceAuthorityService extends IBaseBeanService<ResourceAuthority> {

    /**
     * 验证用户是否有访问url的权限
     *
     * @param userId
     * @param url
     * @param method
     * @return
     */
    boolean decide(Long userId, String url, String method);
}
