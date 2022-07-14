package com.basic.cloud.common.exceptions;

import com.basic.cloud.common.base.ErrorType;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 服务处理异常
 **/
@Data
public class ServiceException extends RuntimeException {

    private Integer code;

    private String msg;

    public ServiceException(String message, Integer code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(ErrorType errorType) {
        this.msg = errorType.getMsg();
        this.code = errorType.getCode();
    }
}
