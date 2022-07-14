package com.basic.cloud.uaa.multiple.authenticator.pwd;

import com.basic.cloud.uaa.multiple.MultipleAuthentication;
import com.basic.cloud.uaa.multiple.authenticator.AbstractMultipleAuthenticator;
import com.basic.cloud.uums.api.UserInfoFeignClient;
import com.basic.cloud.uums.entity.UserInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author lanrenspace@163.com
 * @Description: 默认登录实现
 **/
@Component
@Primary
public class UsernamePasswordAuthenticator extends AbstractMultipleAuthenticator {

    private final UserInfoFeignClient userInfoFeignClient;

    public UsernamePasswordAuthenticator(UserInfoFeignClient userInfoFeignClient) {
        this.userInfoFeignClient = userInfoFeignClient;
    }

    @Override
    public UserInfo authenticate(MultipleAuthentication multipleAuthentication) {
        return userInfoFeignClient.queryUserByAccount(multipleAuthentication.getUsername());
    }

    @Override
    public void pre(MultipleAuthentication multipleAuthentication, HttpServletResponse response) {

    }

    @Override
    public boolean support(MultipleAuthentication multipleAuthentication) {
        return ObjectUtils.isEmpty(multipleAuthentication.getAuthType());
    }
}
