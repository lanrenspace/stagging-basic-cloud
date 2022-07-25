package com.basic.cloud.gateway.filter;

import com.basic.cloud.gateway.contstant.FilterHeaderCont;
import com.basic.cloud.uums.authority.service.AuthService;
import com.basic.cloud.uums.vo.AnonymousInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description: 白名单接口过滤
 **/
@Component
@ComponentScan(basePackages = "com.basic.cloud")
public class AnonymousFilter implements GlobalFilter, Ordered {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(AnonymousFilter.class);

    private final AuthService authService;

    public AnonymousFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<AnonymousInfoVO> anonymousInfos = authService.getAnonymousInfos();
        if (!CollectionUtils.isEmpty(anonymousInfos)) {
            ServerHttpRequest request = exchange.getRequest();
            String url = request.getPath().value();
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            boolean match;
            if (null != route && !ObjectUtils.isEmpty(route.getId())) {
                match = anonymousInfos.stream().anyMatch(anonymousInfoVO -> route.getId().equals(anonymousInfoVO.getAppId())
                        && url.equals(anonymousInfoVO.getUrl()));
            } else {
                match = anonymousInfos.stream().anyMatch(anonymousInfoVO -> url.equals(anonymousInfoVO.getUrl()));
            }
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            if (match) {
                builder.header(FilterHeaderCont.ANONYMOUS_REQ_PARAM, FilterHeaderCont.ANONYMOUS_REQ_VALUE);
                logger.warn("Anonymous Req Url:{}", url);
            } else {
                builder.header(FilterHeaderCont.ANONYMOUS_REQ_PARAM, "-1");
            }
            exchange = exchange.mutate().request(builder.build()).build();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
