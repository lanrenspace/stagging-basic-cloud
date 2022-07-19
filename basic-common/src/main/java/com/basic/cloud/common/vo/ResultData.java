package com.basic.cloud.common.vo;

import com.basic.cloud.common.base.ErrorType;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class ResultData<T> implements Serializable {

    /**
     * 响应状态
     */
    private Integer status;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应消息
     */
    private String msg;

    public ResultData() {

    }

    public ResultData(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultData<T> ok() {
        ResultData<T> response = new ResultData<>();
        response.setStatus(200);
        response.setMsg("request success!");
        return response;
    }

    public static <T> ResultData<T> ok(T data) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(200);
        response.setMsg("request success!");
        response.setData(data);
        return response;
    }

    public static <T> ResultData<T> ok(String msg) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(200);
        response.setMsg(msg);
        return response;
    }


    public static <T> ResultData<T> ok(String msg, T data) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(200);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }


    public static <T> ResultData<T> error() {
        ResultData<T> response = new ResultData<>();
        response.setStatus(500);
        response.setMsg("处理失败");
        return response;
    }

    public static <T> ResultData<T> error(String msg) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(500);
        response.setMsg(msg);
        return response;
    }

    public static <T> ResultData<T> error(ErrorType errorType) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(errorType.getCode());
        resultData.setMsg(errorType.getMsg());
        return resultData;
    }

    public static <T> ResultData<T> error(String msg, T data) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(500);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static <T> ResultData<T> error(String msg, int code) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(code);
        response.setMsg(msg);
        return response;
    }

    public static <T> ResultData<T> error(String msg, int code, T data) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }
}
