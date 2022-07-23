package com.basic.cloud.gateway.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface SwaggerHandler {

    /**
     * 获取ui config
     *
     * @return
     */
    Mono<ServerResponse> uiConfiguration(ServerRequest request);

    /**
     * swagger资源
     *
     * @return
     */
    Mono<ServerResponse> swaggerResource();

    /**
     * 安全配置
     *
     * @return
     */
    Mono<ServerResponse> securityConfiguration(ServerRequest request);
}
