package com.basic.cloud.common.base;

import org.springframework.security.core.Authentication;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface GetUserFromAuthentication {

    /**
     * 获取用户
     *
     * @param authentication
     * @return
     */
    User getUser(Authentication authentication);


    /**
     * 匹配
     *
     * @param authentication
     * @return
     */
    boolean match(Authentication authentication);

}
