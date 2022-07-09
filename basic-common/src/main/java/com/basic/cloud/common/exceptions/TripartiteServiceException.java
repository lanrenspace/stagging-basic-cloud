package com.basic.cloud.common.exceptions;

import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 三方服务异常
 **/
@Data
public class TripartiteServiceException extends RuntimeException {

    /**
     * 三方异常信息
     */
    private String tripartiteMsg;

    /**
     * 错误信息
     */
    private String errorMsg;

    public TripartiteServiceException(String tripartiteMsg) {
        this.tripartiteMsg = tripartiteMsg;
    }

    public TripartiteServiceException(String tripartiteMsg, String errorMsg) {
        this.tripartiteMsg = tripartiteMsg;
        this.errorMsg = errorMsg;
    }
}
