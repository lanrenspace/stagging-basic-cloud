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
@TableName("uum_user_group_role")
public class UserGroupRole extends BisDataEntity<UserGroupRole> {

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
     * 当关联以用户为单位时，这里放的是用户ID，当以组织部门为单位时，这里放的是组织部门ID
     */
    private Integer userGroupId;

    /**
     * 关联类型，1用户为单位，2组织部门为单位
     */
    private Integer userGroupType;

}
