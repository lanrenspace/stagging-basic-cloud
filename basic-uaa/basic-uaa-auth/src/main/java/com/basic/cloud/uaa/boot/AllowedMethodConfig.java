package com.basic.cloud.uaa.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
public class AllowedMethodConfig {

    private final TokenEndpoint tokenEndpoint;

    public AllowedMethodConfig(TokenEndpoint tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    @PostConstruct
    public void reconfigure() {
        Set<HttpMethod> allowedMethods = new HashSet<>(Arrays.asList(HttpMethod.POST, HttpMethod.GET));
        tokenEndpoint.setAllowedRequestMethods(allowedMethods);
    }
}
