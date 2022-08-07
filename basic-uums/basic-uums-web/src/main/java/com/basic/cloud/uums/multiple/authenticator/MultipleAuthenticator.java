package com.basic.cloud.uums.multiple.authenticator;

import com.basic.cloud.uums.multiple.MultipleAuthentication;
import com.basic.cloud.uums.entity.UserInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface MultipleAuthenticator {

    /**
     * 处理认证
     *
     * @param multipleAuthentication
     * @return
     */
    UserInfo authenticate(MultipleAuthentication multipleAuthentication);

    /**
     * 预处理
     *
     * @param multipleAuthentication
     * @param response
     */
    void pre(MultipleAuthentication multipleAuthentication, HttpServletResponse response);

    /**
     * 是否支持集成认证类型
     *
     * @param multipleAuthentication
     * @return
     */
    boolean support(MultipleAuthentication multipleAuthentication);

    /**
     * 认证处理完成后
     *
     * @param multipleAuthentication
     */
    void complete(MultipleAuthentication multipleAuthentication);
}
