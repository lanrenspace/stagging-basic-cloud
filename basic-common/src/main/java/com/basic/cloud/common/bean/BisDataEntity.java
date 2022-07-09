package com.basic.cloud.common.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lanrenspace@163.com
 * @Description: 业务实体
 **/
@Data
public class BisDataEntity<T> implements Serializable {
    /**
     * 创建用户
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected Long createBy;

    /**
     * 创建日期
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 编辑用户
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    protected Long updateBy;

    /**
     * 编辑用户
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    protected Date updateTime;


    /**
     * 是否逻辑删除 1: 已删除 0: 未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    protected Integer delFlag;

    /**
     * 租户编码
     */
    protected String tenantCode;
}
