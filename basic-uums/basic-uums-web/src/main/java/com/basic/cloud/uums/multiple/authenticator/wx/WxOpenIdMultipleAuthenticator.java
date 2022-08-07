package com.basic.cloud.uums.multiple.authenticator.wx;

import com.basic.cloud.common.exceptions.DataException;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.multiple.MultipleAuthentication;
import com.basic.cloud.uums.multiple.authenticator.AbstractMultipleAuthenticator;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author lanrenspace@163.com
 * @Description: 微信登录实现
 **/
@Component
@Primary
@AllArgsConstructor
public class WxOpenIdMultipleAuthenticator extends AbstractMultipleAuthenticator {

    private final UserInfoService userInfoService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfo authenticate(MultipleAuthentication multipleAuthentication) {
        String password = multipleAuthentication.getAuthParameter("password");
        if (ObjectUtils.isEmpty(password)) {
            throw new DataException("请求参数错误,请检查后重试!");
        }
        UserInfo userInfo = userInfoService.queryUserByOpenId(password);
        userInfo.setPassword(passwordEncoder.encode(password));
        return userInfo;
    }

    @Override
    public void pre(MultipleAuthentication multipleAuthentication, HttpServletResponse response) {

    }

    @Override
    public boolean support(MultipleAuthentication multipleAuthentication) {
        String AUTH_TYPE = "wx_openId";
        return AUTH_TYPE.equals(multipleAuthentication.getAuthType());
    }
}