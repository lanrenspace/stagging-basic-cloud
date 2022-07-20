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
@TableName("uum_unit_tenant_type")
public class UnitTenantType extends BisDataEntity<UnitTenantType> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 租户类型ID
     */
    private Long tenantTypeId;

    /**
     * 管理的租户ID
     */
    private Long applyTenantId;
}
