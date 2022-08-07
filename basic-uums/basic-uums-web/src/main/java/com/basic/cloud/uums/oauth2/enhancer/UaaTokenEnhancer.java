package com.basic.cloud.uums.oauth2.enhancer;

import cn.hutool.core.util.ObjectUtil;
import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.common.contstant.SysConst;
import com.basic.cloud.common.exceptions.ServiceException;
import com.basic.cloud.common.utils.RedisUtil;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.entity.UserJwt;
import com.basic.cloud.uums.service.UserInfoService;
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
    private final UserInfoService userInfoService;

    public UaaTokenEnhancer(RedisUtil redisUtil, UserInfoService userInfoService) {
        this.redisUtil = redisUtil;
        this.userInfoService = userInfoService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = Maps.newHashMap();
        additionalInfo.put(SysConst.TOKEN_AFFIX_USER_ACCOUNT, authentication.getName());
        UserJwt details = (UserJwt) authentication.getPrincipal();
        if (null != details) {
            additionalInfo.put(SysConst.TOKEN_AFFIX_USER_ID, details.getId());
            UserDetail userDetail = userInfoService.getUserDetailInfo(details.getId());

            if (!ObjectUtil.isEmpty(userDetail)) {
                redisUtil.set(String.format(SysConst.AUTH_LOGIN_CACHE_KEY, details.getId().toString()), userDetail, accessToken.getExpiresIn() + 300);
            }
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
