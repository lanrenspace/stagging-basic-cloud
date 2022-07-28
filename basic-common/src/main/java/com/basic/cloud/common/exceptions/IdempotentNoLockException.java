package com.basic.cloud.common.exceptions;

/**
 * @Author lanrenspace@163.com
 * @Description: 幂等无法获取锁异常
 **/
public class IdempotentNoLockException extends RuntimeException{

    public IdempotentNoLockException() {
    }

    public IdempotentNoLockException(String message) {
        super(message);
    }

    public IdempotentNoLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdempotentNoLockException(Throwable cause) {
        super(cause);
    }

    public IdempotentNoLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
