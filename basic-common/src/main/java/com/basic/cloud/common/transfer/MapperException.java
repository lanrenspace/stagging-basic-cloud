package com.basic.cloud.common.transfer;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class MapperException extends RuntimeException {

    public MapperException(Exception e) {
        super(e);
    }

    public MapperException(String message) {
        super(message);
    }
}
