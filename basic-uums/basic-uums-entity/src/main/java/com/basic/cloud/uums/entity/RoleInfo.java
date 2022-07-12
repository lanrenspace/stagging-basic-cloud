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
@TableName("uum_role_info")
public class RoleInfo extends BisDataEntity<RoleInfo> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色描述
     */
    private String roleDescription;

    /**
     * 是否公共角色
     */
    private Boolean common;

    /**
     * 角色类型,1管理角色，2业务角色
     */
    private Integer roleType;

    /**
     * 是否可维护
     */
    private Boolean updateTable;

    /**
     * 角色用户数
     */
    private Integer userNumbers;

}
