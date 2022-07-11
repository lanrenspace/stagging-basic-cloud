package com.basic.cloud.uaa.boot;

import com.basic.cloud.common.base.GetUserFromAuthentication;
import com.basic.cloud.common.base.User;
import com.basic.cloud.common.bean.SecurityUser;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class OAuth2GetUserFromAuthentication implements GetUserFromAuthentication {

    @Override
    public User getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Object details = authentication.getDetails();
        SecurityUser securityUser = new SecurityUser();
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            Map<String, Object> claims = jwt.getClaims();
            BeanMap beanMap = BeanMap.create(securityUser);
            beanMap.putAll(claims);
            securityUser.setEnabled(true);
            securityUser.setAccountNonLocked(true);
            securityUser.setAccountNonExpired(true);
            securityUser.setCredentialsNonExpired(true);
            securityUser.setToken(jwt.getTokenValue());
            securityUser.setClientId(claims.get("client_id").toString());
        }
        if (details instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
            securityUser.setIp(webDetails.getRemoteAddress());
        }
        return securityUser;
    }

    @Override
    public boolean match(Authentication authentication) {
        return authentication instanceof JwtAuthenticationToken;
    }
}
