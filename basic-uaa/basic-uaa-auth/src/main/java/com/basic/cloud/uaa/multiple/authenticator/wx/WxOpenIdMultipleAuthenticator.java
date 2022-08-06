package com.basic.cloud.uaa.multiple.authenticator.wx;

import com.basic.cloud.uaa.multiple.MultipleAuthentication;
import com.basic.cloud.uaa.multiple.authenticator.AbstractMultipleAuthenticator;
import com.basic.cloud.uums.api.UserInfoFeignClient;
import com.basic.cloud.uums.entity.UserInfo;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class WxOpenIdMultipleAuthenticator extends AbstractMultipleAuthenticator {

    private final UserInfoFeignClient userInfoFeignClient;

    private String AUTH_TYPE_WX_OPENID = "wx_openid";

    @Override
    public UserInfo authenticate(MultipleAuthentication multipleAuthentication) {
        return userInfoFeignClient.queryUserByOpenId(multipleAuthentication.getOpenId());
    }

    @Override
    public void pre(MultipleAuthentication multipleAuthentication, HttpServletResponse response) {

    }

    @Override
    public boolean support(MultipleAuthentication multipleAuthentication) {
        return multipleAuthentication.getAuthType().equals(AUTH_TYPE_WX_OPENID);
    }
}
