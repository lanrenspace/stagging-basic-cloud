package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.uums.entity.UserUnit;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface UserUnitService extends IBaseBeanService<UserUnit> {

    /**
     * 根据用户ID获取用户所属部门ID
     *
     * @param userId
     * @return
     */
    List<Long> getUnitIdByUserId(Long userId);
}
