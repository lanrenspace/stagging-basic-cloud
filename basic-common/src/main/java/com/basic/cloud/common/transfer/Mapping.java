package com.basic.cloud.common.transfer;

/**
 * @Author lanrenspace@163.com
 * @Description: 自定义转换器
 **/
public interface Mapping<ST, TT> {

    /**
     * 转换
     *
     * @param source
     * @param target
     */
    void mapTo(ST source, TT target);
}
