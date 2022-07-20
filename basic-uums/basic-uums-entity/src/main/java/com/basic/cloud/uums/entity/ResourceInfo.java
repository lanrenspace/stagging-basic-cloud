package com.basic.cloud.uums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 资源信息
 **/
@Data
@TableName("uum_resource_info")
public class ResourceInfo extends BisDataEntity<ResourceInfo> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 资产名称
     */
    private String name;

    /**
     * 资源url
     */
    private String url;

    /**
     * httpMethod
     */
    private String method;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 产品ID
     */
    private Long productId;
}
