package com.basic.cloud.log.annotation;

import java.lang.annotation.*;

/**
 * @Author lanrenspace@163.com
 * @Description:开启数据日志
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EnableDataLog {
}
