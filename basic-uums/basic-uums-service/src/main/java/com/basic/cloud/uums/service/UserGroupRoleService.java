package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.uums.entity.UserGroupRole;
import com.basic.cloud.uums.vo.RoleInfoVO;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface UserGroupRoleService extends IBaseBeanService<UserGroupRole> {

    /**
     * 根据用户ID获取角色编码信息
     *
     * @param userId
     * @return
     */
    List<RoleInfoVO> getRolesByUserId(Long userId);

    /**
     * 根据用户ID获取角色ID
     *
     * @param userId
     * @return
     */
    List<Long> getRoleByUserId(Long userId);
}
