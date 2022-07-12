package com.basic.cloud.uaa.boot;

import com.basic.cloud.common.base.GetUserFromAuthentication;
import com.basic.cloud.common.base.User;
import com.basic.cloud.common.bean.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class OAuth2GetUserFromAuthentication implements GetUserFromAuthentication {

    @Override
    public User getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Object details = authentication.getDetails();
        SecurityUser securityUser = new SecurityUser();
        return securityUser;
    }

    @Override
    public boolean match(Authentication authentication) {
        return false;
    }
}
