package com.basic.cloud.uaa.endpoints;

import com.basic.cloud.common.vo.ResultData;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FrameworkEndpoint
public class TokenRestEndpoint {

    private ConsumerTokenServices tokenServices;

    @DeleteMapping("/oauth/token")
    public ResultData deleteAccessToken(@RequestParam("access_token") String accessToken) {
        tokenServices.revokeToken(accessToken);
        return ResultData.ok();
    }
}
