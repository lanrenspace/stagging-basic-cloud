package com.basic.cloud.uums.authority.service;

import com.basic.cloud.uums.vo.AnonymousInfoVO;
import com.basic.cloud.uums.vo.BlackIpVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;

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

    /**
     * 获取所有的IP黑名单
     *
     * @return
     */
    List<BlackIpVO> getBlackIps();

    /**
     * 获取所有的白名单信息
     *
     * @return
     */
    List<AnonymousInfoVO> getAnonymousInfos();

    /**
     * 资源验证权
     *
     * @param userId
     * @param url
     * @param httpMethod
     * @return
     */
    boolean hasPermission(Long userId, String url, String httpMethod);
}
