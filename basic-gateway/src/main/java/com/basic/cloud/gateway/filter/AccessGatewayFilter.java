package com.basic.cloud.gateway.filter;

import com.basic.cloud.uums.authority.service.AuthService;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
@ComponentScan(basePackages = "com.basic.cloud")
public class AccessGatewayFilter implements GlobalFilter {

    /**
     * log
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String X_USER_ID = "x-user-id";
    private static final String X_USER_NAME = "x-user-name";
    private static final String X_USER_IP = "x-user-ip";

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getPath().value();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        logger.info("Access filter. url:{}, access_token:{}", url, authentication);
        // 有授权请求头,校验权限
        if (!ObjectUtils.isEmpty(authentication)) {
            return this.validateAuthentication(exchange, chain, authentication, url);
        }
        // 不需要网关签权的url，直接返回
        if (authService.ignoreAuthentication(url)) {
            return chain.filter(exchange);
        }
        // 没有authentication且不是忽略权限验证的url，则返回401.
        return unauthorized(exchange);
    }


    /**
     * 验证token以及url权限
     *
     * @param exchange
     * @param chain
     * @param authentication
     * @param url
     * @return
     */
    private Mono<Void> validateAuthentication(ServerWebExchange exchange, GatewayFilterChain chain, String authentication, String url) {
        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethodValue();
        String ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        LinkedHashSet<URI> originUrl = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        Long userId = null;
        String account = null;
        try {
            Jws<Claims> jwt = authService.getJwt(authentication);
            if (null != jwt && null != jwt.getBody()) {
                userId = jwt.getBody().get("userId", Long.class);
                account = jwt.getBody().get("userAccount", String.class);

                ServerHttpRequest.Builder builder = request.mutate();
                if (StringUtils.isNotBlank(account)) {
                    builder.header(X_USER_NAME, account);
                }
                if (!ObjectUtils.isEmpty(userId)) {
                    builder.header(X_USER_ID, userId.toString());
                }
                if (StringUtils.isNotBlank(ip)) {
                    builder.header(X_USER_IP, ip);
                }
                exchange = exchange.mutate().request(builder.build()).build();
                logger.info("userId:{}, userName:{}, access_token:{}, url:{}", userId, account, authentication, url);
            }
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException exception) {
            logger.error("user token error :{}", exception.getMessage());
            if (!authService.ignoreAuthentication(url)) {
                return unauthorized(exchange);
            }
        }
        if (authService.ignoreAuthentication(url)) {
            return chain.filter(exchange);
        }
        boolean hasPermission = true;
        if (hasPermission && !ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(account)) {
            logger.info("User can access. userId:{}, userName:{}, url:{}, method:{}", userId, account, url, method);
            return chain.filter(exchange);
        }
        return forbidden(exchange);
    }

    /**
     * 权限验证未通过
     *
     * @param exchange
     * @return
     */
    private Mono<Void> forbidden(ServerWebExchange exchange) {
        return rebuildExchange(exchange, HttpStatus.FORBIDDEN);
    }

    /**
     * 未登录或token状态异常，返回401
     *
     * @param exchange
     * @return
     */
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        return rebuildExchange(exchange, HttpStatus.UNAUTHORIZED);
    }

    private Mono<Void> rebuildExchange(ServerWebExchange exchange, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        DataBuffer buffer = exchange.getResponse()
                .bufferFactory().wrap(httpStatus.getReasonPhrase().getBytes());
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
