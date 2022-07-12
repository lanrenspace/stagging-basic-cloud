package com.basic.cloud.common.exceptions;

import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class DataException extends RuntimeException{

    /**
     * 错误消息
     */
    private String errorMsg;

    public DataException(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
