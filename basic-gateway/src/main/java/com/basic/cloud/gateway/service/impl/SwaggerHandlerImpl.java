package com.basic.cloud.gateway.service.impl;

import com.basic.cloud.gateway.service.SwaggerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class SwaggerHandlerImpl implements SwaggerHandler {

    private final UiConfiguration uiConfiguration;
    private final SwaggerResourcesProvider swaggerResources;
    private SecurityConfiguration securityConfiguration;

    public SwaggerHandlerImpl(@Autowired(required = false) UiConfiguration uiConfiguration, SwaggerResourcesProvider swaggerResources,
                              @Autowired(required = false) SecurityConfiguration securityConfiguration) {
        this.uiConfiguration = uiConfiguration;
        this.swaggerResources = swaggerResources;
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public Mono<ServerResponse> uiConfiguration() {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build())));
    }

    @Override
    public Mono<ServerResponse> swaggerResource() {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(swaggerResources.get()));
    }

    @Override
    public Mono<ServerResponse> securityConfiguration() {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build())));
    }
}
