package com.basic.cloud.uums.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lanrenspace@163.com
 * @Description: 角色信息
 **/
@Data
public class RoleInfoVO implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;
}
