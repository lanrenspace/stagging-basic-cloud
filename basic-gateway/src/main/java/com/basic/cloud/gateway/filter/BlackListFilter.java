package com.basic.cloud.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class BlackListFilter implements GlobalFilter, Ordered {

    /**
     * ip黑名单列表
     */
    @Value("${black.ips}")
    private String blackList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取客户端ip
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String clientIp = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        if (!ObjectUtils.isEmpty(blackList)) {
            if (Arrays.asList(blackList.split(",")).contains(clientIp)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                String data = "拒绝访问!";
                DataBuffer dataBuffer = response.bufferFactory().wrap(data.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(dataBuffer));
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
