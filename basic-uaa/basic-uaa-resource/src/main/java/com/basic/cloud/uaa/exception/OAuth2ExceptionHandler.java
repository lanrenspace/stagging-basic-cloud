package com.basic.cloud.uaa.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@JsonSerialize(using = OAuth2ExceptionJacksonSerializer.class)
public class OAuth2ExceptionHandler extends OAuth2Exception {

    private String msg;

    private Object data;

    public OAuth2ExceptionHandler(String msg, Object t) {
        super(msg);
        this.msg = msg;
        this.data = t;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
