package com.basic.cloud.common.annotion;

import java.lang.annotation.*;

/**
 * @Author lanrenspace@163.com
 * @Description: 转换属性
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface MappingField {

    /**
     * 名称
     *
     * @return
     */
    String name() default "";

    /**
     * 目标类型
     *
     * @return
     */
    Class[] targetClass() default {};

    Class postFilter() default void.class;
}
