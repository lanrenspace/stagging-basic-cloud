package com.basic.cloud.log.contstant;

/**
 * @Author lanrenspace@163.com
 * @Description: 操作日志类型
 **/
public enum LogOperateTypeEnum {

    OPERATE_TYPE_1(1, "操作日志类型:查询:"),
    OPERATE_TYPE_2(2, "操作日志类型:添加:"),
    OPERATE_TYPE_3(3, "操作日志类型:更新:"),
    OPERATE_TYPE_4(4, "操作日志类型:删除:"),
    OPERATE_TYPE_5(5, "操作日志类型:导入:"),
    OPERATE_TYPE_6(6, "操作日志类型:导出:"),
    OPERATE_TYPE_7(7, "操作日志类型:三方交互:"),
    LOG_TYPE_00(-1, "操作日志类型:其他:");

    /**
     * 值
     */
    private final int code;
    /**
     * 描述
     */
    private final String description;

    LogOperateTypeEnum(int code, String description) {
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
