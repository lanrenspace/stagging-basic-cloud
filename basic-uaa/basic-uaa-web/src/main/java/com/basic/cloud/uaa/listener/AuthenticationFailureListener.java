package com.basic.cloud.uaa.listener;

import com.basic.cloud.uums.api.UserExtFeignClient;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author lanrenspace@163.com
 * @Description: 登录失败监听器
 **/
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserExtFeignClient userExtFeignClient;

    public AuthenticationFailureListener(UserExtFeignClient userExtFeignClient) {
        this.userExtFeignClient = userExtFeignClient;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String userAccount = (String) event.getAuthentication().getPrincipal();
        if (!StringUtils.isEmpty(userAccount)) {
            userExtFeignClient.updateUserLoginFails(userAccount, false);
        }
    }
}
