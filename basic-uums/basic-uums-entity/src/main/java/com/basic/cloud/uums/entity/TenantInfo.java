package com.basic.cloud.uums.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@TableName("uum_tenant_info")
public class TenantInfo extends BisDataEntity<TenantInfo> {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 管理员ID
     */
    private Long adminId;

    /**
     * 管理员账号
     */
    private String adminAccount;

    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 是否有效
     */
    private Boolean valid;

    /**
     * 租户全称
     */
    private String tenantName;

    /**
     * logo
     */
    private String logo;

    /**
     * 简介
     */
    private String description;

    /**
     * 审核日期
     */
    private Date auditDate;

    /**
     * 备注信息
     */
    private String remark;

}
