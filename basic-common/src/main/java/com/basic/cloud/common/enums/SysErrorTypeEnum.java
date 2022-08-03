package com.basic.cloud.common.enums;

import com.basic.cloud.common.base.ErrorType;
import lombok.Getter;

/**
 * @Author lanrenspace@163.com
 * @Description: 系统异常类型定义
 **/
@Getter
public enum SysErrorTypeEnum implements ErrorType {
    SYSTEM_ERROR(-1, "系统异常"),
    DATA_ERROR(1000, "数据异常!"),
    GATEWAY_ERROR(4416, "网关异常"),
    GATEWAY_CONNECT_TIME_OUT(4098, "网关超时"),
    GATEWAY_NOT_FOUND_SERVICE(4356, "服务未找到"),
    INVALID_TOKEN(8193, "无效token"),
    PERMISSION_ERROR(401, "PERMISSION_ERROR"),
    ;

    SysErrorTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误类型码
     */
    private Integer code;
    /**
     * 错误类型描述信息
     */
    private String msg;
}
