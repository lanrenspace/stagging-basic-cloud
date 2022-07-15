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
     * 创建用户名称
     */
    @TableField(value = "create_name", fill = FieldFill.INSERT)
    protected String createName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 编辑用户
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    protected Long updateBy;

    /**
     * 编辑用户名称
     */
    @TableField(value = "update_name", fill = FieldFill.UPDATE)
    protected Long updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    protected Date updateTime;


    /**
     * 是否逻辑删除 1: 已删除 0: 未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    protected Boolean delFlag;

    /**
     * 租户编码
     */
    @TableField(value = "tenant_code", fill = FieldFill.INSERT)
    protected String tenantCode;
}
