package com.basic.cloud.uaa.endpoints;

import com.basic.cloud.common.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
public class TokenRestEndpoint {

    /**
     * 请求安全头
     */
    private static String AUTH_PRE = "Authorization";
    private static String BEARER_HEADER = "Bearer";

    @Autowired
    private ConsumerTokenServices tokenServices;

    /**
     * 退出时销毁token
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/token/remove", method = RequestMethod.POST)
    public ResultData logout(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_PRE);
        if (authHeader != null && authHeader.contains(BEARER_HEADER)) {
            Matcher matcher = Pattern.compile(BEARER_HEADER, Pattern.CASE_INSENSITIVE).matcher(authHeader);
            String tokenValue = matcher.replaceFirst("").trim();
            tokenServices.revokeToken(tokenValue);
        }
        return ResultData.ok();
    }

}
