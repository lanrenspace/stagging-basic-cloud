package com.basic.cloud.common.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class ResultData implements Serializable {

    /**
     * 响应状态
     */
    private Integer status;

    /**
     * 响应数据
     */
    private Object data;

    /**
     * 响应消息
     */
    private String msg;

    public ResultData() {

    }

    public ResultData(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static ResultData ok() {
        ResultData response = new ResultData();
        response.setStatus(200);
        response.setMsg("request success!");
        return response;
    }

    public static ResultData ok(Object data) {
        ResultData response = new ResultData();
        response.setStatus(HttpStatus.OK.value());
        response.setMsg("request success!");
        response.setData(data);
        return response;
    }

    public static ResultData ok(String msg) {
        ResultData response = new ResultData();
        response.setStatus(200);
        response.setMsg(msg);
        return response;
    }


    public static ResultData ok(String msg, Object data) {
        ResultData response = new ResultData();
        response.setStatus(200);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }


    public static ResultData error() {
        ResultData response = new ResultData();
        response.setStatus(500);
        response.setMsg("处理失败");
        return response;
    }

    public static ResultData error(String msg) {
        ResultData response = new ResultData();
        response.setStatus(500);
        response.setMsg(msg);
        return response;
    }

    public static ResultData error(String msg, Object data) {
        ResultData response = new ResultData();
        response.setStatus(500);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static ResultData error(String msg, int code) {
        ResultData response = new ResultData();
        response.setStatus(code);
        response.setMsg(msg);
        return response;
    }

    public static ResultData error(String msg, int code, Object data) {
        ResultData response = new ResultData();
        response.setStatus(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }
}
