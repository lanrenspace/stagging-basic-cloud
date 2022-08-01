package com.basic.cloud.log.contstant;

/**
 * @Author lanrenspace@163.com
 * @Description: 日志类型常量
 **/
public enum LogTypeEnum {

    LOG_TYPE_0(0, "系统日志类型:注册:"),
    LOG_TYPE_1(1, "系统日志类型:登录:"),
    LOG_TYPE_2(2, "系统日志类型:操作:"),
    LOG_TYPE_3(3, "系统日志类型:定时任务:"),
    LOG_TYPE_4(4, "系统日志类型:退出:"),
    LOG_TYPE_5(5, "系统日志类型:三方交互:"),
    LOG_TYPE_00(-1, "系统日志类型:其他:");

    /**
     * 值
     */
    private final int code;
    /**
     * 描述
     */
    private final String description;

    LogTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
