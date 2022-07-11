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
@TableName("uum_tenant_menu_template")
public class TenantMenuTemplate extends BisDataEntity<TenantMenuTemplate> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否自定义，Y是，N不是
     */
    private Boolean custom;

}
