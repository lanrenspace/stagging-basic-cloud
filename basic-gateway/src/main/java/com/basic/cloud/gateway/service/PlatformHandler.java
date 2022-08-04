package com.basic.cloud.gateway.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @Author lanrenspace@163.com
 * @Description: 系统服务
 **/
public interface PlatformHandler {

    /**
     * 初始化路由资源url信息
     *
     * @param request
     * @return
     */
    Mono<ServerResponse> initRouteResourceUrl(ServerRequest request);
}
