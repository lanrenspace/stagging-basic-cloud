package com.basic.cloud.common.contstant;

/**
 * Create by HeLongJun on 2021/5/17 14:12
 *
 * @author Administrator
 * @Description:
 */
public enum DelEnum {

    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除");

    /**
     * 值
     */
    private int code;
    /**
     * 描述
     */
    private String description;

    DelEnum(int code, String description) {
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
