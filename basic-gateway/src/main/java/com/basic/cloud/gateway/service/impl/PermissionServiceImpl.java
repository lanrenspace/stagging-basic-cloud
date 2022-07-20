package com.basic.cloud.gateway.service.impl;

import com.basic.cloud.gateway.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Override
    public boolean permission(String authentication, String userId, String url, String method) {
        return false;
    }
}
