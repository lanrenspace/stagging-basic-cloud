package com.basic.cloud.uaa.boot;

import com.basic.cloud.common.base.GetUserFromAuthentication;
import com.basic.cloud.common.base.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @Author lanrenspace@163.com
 * @Description: 用户名密码表单登录模式
 **/
@Component
public class UserNamePasswordGetUserFromAuthentication implements GetUserFromAuthentication {

    @Override
    public User getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Object details = authentication.getDetails();
        User user = null;
        if (principal instanceof UserDetails) {
            user = (User) principal;
        }
        if (details instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
            assert user != null;
            user.setIp(webDetails.getRemoteAddress());
        }
        return user;
    }

    @Override
    public boolean match(Authentication authentication) {
        return authentication instanceof UsernamePasswordAuthenticationToken;
    }
}
