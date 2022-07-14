package com.basic.cloud.common.exceptions;

import com.basic.cloud.common.vo.ResultData;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestControllerAdvice
public class GlobalValidExceptionHandler {

    /**
     * 统一异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultData> handlerException(Exception ex) {
        ResultData resultData;
        String errorMsg = "Unknown exception!";
        if (ex instanceof BindException) {
            // 校验异常
            errorMsg = ((BindException) ex).getFieldErrors().parallelStream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s1, s2) -> s1 + s2);
            resultData = ResultData.error(errorMsg, ((BindException) ex).getTarget());
        } else if (ex instanceof WebExchangeBindException) {
            errorMsg = ((WebExchangeBindException) ex).getFieldErrors().parallelStream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("",
                    (s1, s2) -> s1 + s2);
            resultData = ResultData.error(errorMsg, ((WebExchangeBindException) ex).getTarget());
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            errorMsg = ex.getMessage();
            resultData = ResultData.error(errorMsg, ex.getMessage());
        } else {
            ex.printStackTrace();
            resultData = ResultData.error(errorMsg, ex.getMessage());
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
