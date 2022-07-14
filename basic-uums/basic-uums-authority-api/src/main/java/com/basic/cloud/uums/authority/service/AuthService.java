package com.basic.cloud.uums.authority.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface AuthService {

    /**
     * 提取jwt token 对象
     *
     * @param jwtToken
     * @return
     */
    Jws<Claims> getJwt(String jwtToken);

    /**
     * 判断url是否忽略授权
     *
     * @param url
     * @return
     */
    boolean ignoreAuthentication(String url);
}
