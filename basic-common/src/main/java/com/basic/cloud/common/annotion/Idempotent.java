package com.basic.cloud.common.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author lanrenspace@163.com
 * @Description: 幂等切入
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 幂等 key, 可以使用 SPEL 表达式
     *
     * @return
     */
    String key();

    /**
     * 加锁最长时间
     *
     * @return
     */
    long maxLockMilli() default 10000L;

    /**
     * key 生成器, 指定springBeanName
     *
     * @return
     */
    String generator() default "";

    /**
     * 指定幂等管理器BeanName, 多个管理器情况下使用 @Primary 的管理器, 默认的是 REDIS方式
     * <p>
     *
     * @return
     */
    String idempotentManager() default "";

}
