package com.basic.cloud.gateway.routes;

import com.basic.cloud.gateway.service.PlatformHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
public class PlatformRouter {

    @Bean
    public RouterFunction<ServerResponse> platformRouters(PlatformHandler platformHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/platform/init-route-resource-url")
                .and(RequestPredicates.accept(MediaType.ALL)), platformHandler::initRouteResourceUrl);
    }
}
