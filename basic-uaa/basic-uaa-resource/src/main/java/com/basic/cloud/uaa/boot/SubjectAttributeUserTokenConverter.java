package com.basic.cloud.uaa.boot;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class SubjectAttributeUserTokenConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) super.extractAuthentication(map);
        oAuth2Authentication.setDetails(map);
        return oAuth2Authentication;
    }
}
