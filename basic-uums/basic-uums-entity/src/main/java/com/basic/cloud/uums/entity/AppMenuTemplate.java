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
@TableName("uum_app_menu_template")
public class AppMenuTemplate extends BisDataEntity<AppMenuTemplate> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板描述
     */
    private String templateDesc;

    /**
     * 模板分类
     */
    private Long tenantTypeId;

    /**
     * 是否租户模板
     */
    private Boolean tenant;

    /**
     * 产品ID
     */
    private Long productId;

}
