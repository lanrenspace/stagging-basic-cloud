package com.basic.cloud.gateway.exception;

import com.basic.cloud.common.enums.SysErrorTypeEnum;
import com.basic.cloud.common.vo.ResultData;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.netty.channel.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class GateWayExceptionHandlerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResultData handle(ResponseStatusException ex) {
        log.error("response status exception:{}", ex.getMessage());
        return ResultData.error(SysErrorTypeEnum.GATEWAY_ERROR);
    }

    @ExceptionHandler(value = {ConnectTimeoutException.class})
    public ResultData handle(ConnectTimeoutException ex) {
        log.error("connect timeout exception:{}", ex.getMessage());
        return ResultData.error(SysErrorTypeEnum.GATEWAY_CONNECT_TIME_OUT);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultData handle(NotFoundException ex) {
        log.error("not found exception:{}", ex.getMessage());
        return ResultData.error(SysErrorTypeEnum.GATEWAY_NOT_FOUND_SERVICE);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultData handle(ExpiredJwtException ex) {
        log.error("ExpiredJwtException:{}", ex.getMessage());
        return ResultData.error(SysErrorTypeEnum.INVALID_TOKEN);
    }

    @ExceptionHandler(value = {SignatureException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultData handle(SignatureException ex) {
        log.error("SignatureException:{}", ex.getMessage());
        return ResultData.error(SysErrorTypeEnum.INVALID_TOKEN);
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultData handle(MalformedJwtException ex) {
        log.error("MalformedJwtException:{}", ex.getMessage());
        return ResultData.error(SysErrorTypeEnum.INVALID_TOKEN);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData handle(RuntimeException ex) {
        log.error("runtime exception:{}", ex.getMessage());
        return ResultData.error();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData handle(Exception ex) {
        log.error("exception:{}", ex.getMessage());
        return ResultData.error();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData handle(Throwable throwable) {
        ResultData result = ResultData.error();
        if (throwable instanceof ResponseStatusException) {
            result = handle((ResponseStatusException) throwable);
        } else if (throwable instanceof ConnectTimeoutException) {
            result = handle((ConnectTimeoutException) throwable);
        } else if (throwable instanceof NotFoundException) {
            result = handle((NotFoundException) throwable);
        } else if (throwable instanceof RuntimeException) {
            result = handle((RuntimeException) throwable);
        } else if (throwable instanceof Exception) {
            result = handle((Exception) throwable);
        }
        return result;
    }
}
