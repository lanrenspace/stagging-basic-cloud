package com.basic.cloud.uums.enums;

import com.basic.cloud.common.base.ErrorType;
import lombok.Getter;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Getter
public enum UumErrorTypeEnum implements ErrorType {

    ACCOUNT_LOCKED(10003, "账号已被锁定!");

    UumErrorTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String msg;
}
