package com.basic.cloud.gateway.routes;

import com.basic.cloud.gateway.service.SwaggerHandler;
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
public class SwaggerRouter {

    @Bean
    public RouterFunction<ServerResponse> swaggerRouters(SwaggerHandler swaggerHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/swagger-resources/configuration/ui")
                        .and(RequestPredicates.accept(MediaType.ALL)), serverRequest -> swaggerHandler.uiConfiguration())
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/security")
                        .and(RequestPredicates.accept(MediaType.ALL)), serverRequest -> swaggerHandler.securityConfiguration())
                .andRoute(RequestPredicates.GET("/swagger-resources")
                        .and(RequestPredicates.accept(MediaType.ALL)), serverRequest -> swaggerHandler.swaggerResource());
    }
}
