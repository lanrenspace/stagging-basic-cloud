package com.basic.cloud.uaa.oauth2.enhancer;

import com.basic.cloud.uaa.entity.UserJwt;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description: 自定义token携带信息
 **/
@Slf4j
public class UaaTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = Maps.newHashMap();
        additionalInfo.put("userAccount", authentication.getName());
        try {
            UserJwt details = (UserJwt) authentication.getPrincipal();
            if (null != details) {
                additionalInfo.put("userId", details.getId());
            }
        } catch (Exception e) {
            log.error("user name:{}", authentication.getName());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
