package com.basic.cloud.common.bean;

import com.basic.cloud.common.annotion.MappingData;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description: 登录用户信息
 **/
@MappingData
@Data
public class UserDetail implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 电话
     */
    private String mobile;

    /**
     * email
     */
    private String email;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 三方编码
     */
    private String thirdNo;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 角色信息
     */
    private List<RoleInfo> roleInfos;

    /**
     * 机构信息
     */
    private List<UnitInfo> unitInfos;


    /**
     * 角色信息
     */
    @MappingData
    @Data
    public static class RoleInfo {

        /**
         * 角色ID
         */
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
         * 角色类型
         */
        private Integer roleType;
    }

    /**
     * 组织机构信息
     */
    @MappingData
    @Data
    public static class UnitInfo {

        /**
         * 组织机构ID
         */
        private Long id;

        /**
         * 编码
         */
        private String unitCode;

        /**
         * 名称
         */
        private String unitName;

        /**
         * 机构类型
         */
        private Integer unitType;

        /**
         * 机构全路径名称
         */
        private String unitFullName;
    }
}
