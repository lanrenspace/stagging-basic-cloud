package com.basic.cloud.log.annotation;

import com.basic.cloud.log.contstant.LogOperateTypeEnum;
import com.basic.cloud.log.contstant.LogTypeEnum;

import java.lang.annotation.*;

/**
 * @Author lanrenspace@163.com
 * @Description:开启日志记录
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EnableLog {

    /**
     * 日志内容
     *
     * @return
     */
    String value() default "";

    /**
     * 日志类型
     *
     * @return 参考com.basic.cloud.log.contstant.LogTypeEnum
     */
    LogTypeEnum logType() default LogTypeEnum.LOG_TYPE_2;

    /**
     * 操作日志类型
     *
     * @return 参考 com.basic.cloud.log.contstant.LogOperateTypeEnum
     */
    LogOperateTypeEnum operateType() default LogOperateTypeEnum.LOG_TYPE_00;

    /**
     * 是否开启数据日志记录
     *
     * @return
     */
    boolean dataLog() default false;
}
