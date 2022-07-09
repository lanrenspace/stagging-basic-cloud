package com.basic.cloud.common.annotion;

import java.lang.annotation.*;

/**
 * @Author lanrenspace@163.com
 * @Description: 要转换的数据对象
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappingData {

    /**
     * 目标class
     *
     * @return
     */
    Class[] targetClass() default {};

    /**
     * 类型过滤
     *
     * @return
     */
    Class filter() default void.class;
}
