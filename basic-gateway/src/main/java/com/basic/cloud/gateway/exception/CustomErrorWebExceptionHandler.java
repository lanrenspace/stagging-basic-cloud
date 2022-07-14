package com.basic.cloud.gateway.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private GateWayExceptionHandlerAdvice gateWayExceptionHandlerAdvice;

    public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext, GateWayExceptionHandlerAdvice gateWayExceptionHandlerAdvice) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
        this.gateWayExceptionHandlerAdvice = gateWayExceptionHandlerAdvice;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> error = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus errorStatus = getHttpStatus(error);
        Throwable throwable = getError(request);
        return ServerResponse.status(errorStatus)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(gateWayExceptionHandlerAdvice.handle(throwable)));
    }
}
