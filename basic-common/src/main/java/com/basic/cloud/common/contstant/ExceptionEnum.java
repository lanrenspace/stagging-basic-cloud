package com.basic.cloud.common.contstant;

/**
 * @Author lanrenspace@163.com
 * @Description: 异常定义
 **/
public enum ExceptionEnum {
    SERVICE_ERROR(500, "service exception!");

    private Integer code;
    private String errorMsg;

    ExceptionEnum(Integer code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
