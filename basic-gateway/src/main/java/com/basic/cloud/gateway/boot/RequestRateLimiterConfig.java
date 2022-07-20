package com.basic.cloud.gateway.boot;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Author lanrenspace@163.com
 * @Description: 自定义限流
 **/
@Component
public class RequestRateLimiterConfig {

    /**
     * ip地址限流
     *
     * @return 限流key
     */
    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
    }
}
