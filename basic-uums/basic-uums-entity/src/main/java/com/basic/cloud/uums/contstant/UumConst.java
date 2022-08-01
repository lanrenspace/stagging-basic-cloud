package com.basic.cloud.uums.contstant;

/**
 * @Author lanrenspace@163.com
 * @Description: 用户模板相关常量
 **/
public interface UumConst {


    /**
     * 用户关联类型
     */
    interface UserGroupType {

        /**
         * 用户
         */
        Integer USER = 1;

        /**
         * 组织部门
         */
        Integer ORG = 2;
    }

    /**
     * 用户状态
     */
    interface UserStatus {
        /**
         * 活跃的
         */
        Integer ACTIVE = 1;

        /**
         * 离职
         */
        Integer QUIT = 2;
    }
}
