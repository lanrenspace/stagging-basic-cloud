package com.basic.cloud.uaa.multiple.authenticator;

import com.basic.cloud.uaa.multiple.MultipleAuthentication;
import com.basic.cloud.uums.entity.UserInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public abstract class AbstractMultipleAuthenticator implements MultipleAuthenticator {

    @Override
    public abstract UserInfo authenticate(MultipleAuthentication multipleAuthentication);

    @Override
    public abstract void pre(MultipleAuthentication multipleAuthentication, HttpServletResponse response);

    @Override
    public abstract boolean support(MultipleAuthentication multipleAuthentication);

    @Override
    public void complete(MultipleAuthentication multipleAuthentication) {

    }
}
