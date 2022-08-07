package com.basic.cloud.uums.multiple.authenticator.pwd;

import com.basic.cloud.uums.multiple.MultipleAuthentication;
import com.basic.cloud.uums.multiple.authenticator.AbstractMultipleAuthenticator;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.service.UserInfoService;
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

    private final UserInfoService userInfoService;

    public UsernamePasswordAuthenticator(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public UserInfo authenticate(MultipleAuthentication multipleAuthentication) {
        return userInfoService.queryUserByAccount(multipleAuthentication.getUsername());
    }

    @Override
    public void pre(MultipleAuthentication multipleAuthentication, HttpServletResponse response) {

    }

    @Override
    public boolean support(MultipleAuthentication multipleAuthentication) {
        return ObjectUtils.isEmpty(multipleAuthentication.getAuthType());
    }
}
