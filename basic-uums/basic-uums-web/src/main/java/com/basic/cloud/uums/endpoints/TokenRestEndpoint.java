package com.basic.cloud.uums.endpoints;


import com.basic.cloud.common.vo.ResultData;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
public class TokenRestEndpoint {

    private ConsumerTokenServices tokenServices;

    public TokenRestEndpoint(ConsumerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    /**
     * 删除token
     *
     * @param accessToken
     * @return
     */
    @DeleteMapping("/oauth/token/remove")
    public ResultData<Void> deleteAccessToken(String accessToken) {
        if (ObjectUtils.isEmpty(accessToken)) {
            return ResultData.error("请求参数不能为空!");
        }
        tokenServices.revokeToken(accessToken);
        return ResultData.ok();
    }
}
