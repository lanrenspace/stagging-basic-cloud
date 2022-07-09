package com.basic.cloud.common.base;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FunctionalInterface
public interface IdInjectionStrategy {

    /**
     * 返回一个ID
     *
     * @return
     */
    Object id();
}
