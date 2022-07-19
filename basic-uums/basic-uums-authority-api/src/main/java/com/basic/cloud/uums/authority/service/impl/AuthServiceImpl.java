package com.basic.cloud.uums.authority.service.impl;

import com.basic.cloud.uums.api.BlackIpsFeignClient;
import com.basic.cloud.uums.authority.service.AuthService;
import com.basic.cloud.uums.vo.BlackIpVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class AuthServiceImpl implements AuthService {

    private final BlackIpsFeignClient blackIpsFeignClient;

    public AuthServiceImpl(BlackIpsFeignClient blackIpsFeignClient) {
        this.blackIpsFeignClient = blackIpsFeignClient;
    }

    /**
     * Authorization认证头
     */
    private static final String BEARER = "Bearer ";

    /**
     * jwt 签名
     */
    @Value("${uaa.auth.jwt.signingKey}")
    private String signingKey;

    /**
     * 不需要网关签权的url配置(/oauth,/open)
     */
    @Value("${gate.ignore.authentication.startWith:/oauth,/open}")
    private String ignoreUrls = "/oauth,/open";

    /**
     * 内部调用不需要网关鉴权
     */
    @Value("${gate.internal.call.startWith:/feign}")
    private String internalCall = "/feign";

    @Override
    public Jws<Claims> getJwt(String jwtToken) {
        if (jwtToken.startsWith(BEARER.toLowerCase()) || jwtToken.startsWith(BEARER.toUpperCase())) {
            jwtToken = StringUtils.substring(jwtToken, BEARER.length());
        }
        return Jwts.parser()
                .setSigningKey(signingKey.getBytes())
                .parseClaimsJws(jwtToken);
    }

    @Override
    public boolean ignoreAuthentication(String url) {
        return Stream.of(this.ignoreUrls.split(",")).anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
    }

    @Override
    public List<BlackIpVO> getBlackIps() {
        return blackIpsFeignClient.getAllBlackIps();
    }
}
