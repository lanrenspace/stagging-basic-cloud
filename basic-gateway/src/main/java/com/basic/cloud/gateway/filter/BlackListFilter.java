package com.basic.cloud.gateway.filter;

import com.basic.cloud.uums.authority.service.AuthService;
import com.basic.cloud.uums.vo.BlackIpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class BlackListFilter implements GlobalFilter, Ordered {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BlackListFilter.class);

    private final AuthService authService;

    public BlackListFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取客户端ip
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String clientIp = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        List<BlackIpVO> allBlackIp = this.authService.getBlackIps();
        if (!CollectionUtils.isEmpty(allBlackIp)) {
            Optional<BlackIpVO> optional = allBlackIp.stream().filter(blackIpVO -> clientIp.equals(blackIpVO.getIp())).findFirst();
            if (optional.isPresent()) {
                BlackIpVO blackIpVO = optional.get();
                logger.error("禁止访问=============> ip: {} 截止时间：{}", blackIpVO.getIp(), blackIpVO.getDeadlineDate());
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                String data = "拒绝访问!";
                DataBuffer dataBuffer = response.bufferFactory().wrap(data.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(dataBuffer));
            }
        } else {
            logger.warn("Black Ip filter: IP interception is not configured.");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
