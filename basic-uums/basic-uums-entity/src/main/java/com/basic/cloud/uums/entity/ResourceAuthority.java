package com.basic.cloud.uums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.basic.cloud.common.bean.BisDataEntity;
import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description: 资源权限
 **/
@Data
@TableName("uum_resource_authority")
public class ResourceAuthority extends BisDataEntity<ResourceAuthority> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 资源类型
     */
    private Integer resourceType;

    /**
     * 资源操作类型,1可以使用,2可分配
     */
    private Long resourceOpt;

    /**
     * 描述
     */
    private String authorityDesc;

}
