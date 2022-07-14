package com.basic.cloud.uaa.listener;

import com.basic.cloud.uums.api.UserExtFeignClient;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserExtFeignClient userExtFeignClient;

    public AuthenticationSuccessEventListener(UserExtFeignClient userExtFeignClient) {
        this.userExtFeignClient = userExtFeignClient;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
    }
}
