package com.basic.cloud.uums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
@TableName("uum_tenant_type")
public class TenantType extends BisDataEntity<TenantType> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 租户类型名称
     */
    private String tenantTypeName;

    /**
     * 行业类别
     */
    private String industry;

    /**
     * 是否可修改,Y可维护，N不可维护
     */
    private Boolean updatable;

    /**
     * 初始套餐模板ID
     */
    private Long initTemplateId;

    /**
     * 默认套餐模板ID
     */
    private Long defaultTemplateId;

    /**
     * 上级租户类型ID
     */
    private Long tenantParentTypeId;

    /**
     * 租户类型编码
     */
    private String tenantTypeCode;

    /**
     * 备注
     */
    private String remark;

}
