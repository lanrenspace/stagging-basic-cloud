package com.basic.cloud.common.vo;

import com.basic.cloud.common.base.ErrorType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class ResultData<T> implements Serializable {

    /**
     * 响应状态
     */
    @Getter
    @Setter
    private Integer status;

    /**
     * 响应数据
     */
    @Getter
    @Setter
    private T data;

    /**
     * 响应消息
     */
    @Getter
    @Setter
    private String msg;

    /**
     * 是否成功
     */
    @Getter
    private Boolean success;

    public ResultData() {

    }

    public ResultData(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.success = status == 200;
    }

    public static <T> ResultData<T> ok() {
        ResultData<T> response = new ResultData<>();
        response.setStatus(200);
        response.setMsg("request success!");
        return setSuccess(response);
    }

    public static <T> ResultData<T> ok(T data) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(200);
        response.setMsg("request success!");
        response.setData(data);
        return setSuccess(response);
    }

    public static <T> ResultData<T> ok(String msg) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(200);
        response.setMsg(msg);
        return setSuccess(response);
    }


    public static <T> ResultData<T> ok(String msg, T data) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(200);
        response.setMsg(msg);
        response.setData(data);
        return setSuccess(response);
    }


    public static <T> ResultData<T> error() {
        ResultData<T> response = new ResultData<>();
        response.setStatus(500);
        response.setMsg("处理失败");
        return setSuccess(response);
    }

    public static <T> ResultData<T> error(String msg) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(500);
        response.setMsg(msg);
        return setSuccess(response);
    }

    public static <T> ResultData<T> error(ErrorType errorType) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(errorType.getCode());
        resultData.setMsg(errorType.getMsg());
        return setSuccess(resultData);
    }

    public static <T> ResultData<T> error(String msg, T data) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(500);
        response.setMsg(msg);
        response.setData(data);
        return setSuccess(response);
    }

    public static <T> ResultData<T> error(String msg, int code) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(code);
        response.setMsg(msg);
        return setSuccess(response);
    }

    public static <T> ResultData<T> error(String msg, int code, T data) {
        ResultData<T> response = new ResultData<>();
        response.setStatus(code);
        response.setMsg(msg);
        response.setData(data);
        return setSuccess(response);
    }

    /**
     * 设置响应状态
     *
     * @param resultData
     * @param <T>
     * @return
     */
    private static <T> ResultData<T> setSuccess(ResultData<T> resultData) {
        resultData.success = !ObjectUtils.isEmpty(resultData.getStatus()) && resultData.getStatus() == 200;
        return resultData;
    }

}
