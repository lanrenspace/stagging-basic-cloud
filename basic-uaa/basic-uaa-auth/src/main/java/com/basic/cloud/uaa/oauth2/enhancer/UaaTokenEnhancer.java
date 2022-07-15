package com.basic.cloud.uaa.oauth2.enhancer;

import com.basic.cloud.common.contstant.CacheDefine;
import com.basic.cloud.common.contstant.SysConst;
import com.basic.cloud.common.enums.SysErrorTypeEnum;
import com.basic.cloud.common.exceptions.ServiceException;
import com.basic.cloud.common.utils.RedisUtil;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uaa.entity.UserJwt;
import com.basic.cloud.uums.api.UserInfoFeignClient;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    private final RedisUtil redisUtil;
    private final UserInfoFeignClient userInfoFeignClient;

    public UaaTokenEnhancer(RedisUtil redisUtil, UserInfoFeignClient userInfoFeignClient) {
        this.redisUtil = redisUtil;
        this.userInfoFeignClient = userInfoFeignClient;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = Maps.newHashMap();
        additionalInfo.put(SysConst.TOKEN_AFFIX_USER_ACCOUNT, authentication.getName());
        UserJwt details = (UserJwt) authentication.getPrincipal();
        if (null != details) {
            additionalInfo.put(SysConst.TOKEN_AFFIX_USER_ID, details.getId());
            ResultData resultData = userInfoFeignClient.getUserDetailInfo(details.getId());
            if (resultData.getStatus() != HttpStatus.OK.value()) {
                throw new ServiceException(resultData.getStatus(), resultData.getMsg());
            }
            if (resultData.getData() != null) {
                redisUtil.set(String.format(SysConst.AUTH_LOGIN_CACHE_KEY, details.getId().toString()), resultData.getData());
            }
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
