package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.uums.entity.UnitInfo;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface UnitInfoService extends IBaseBeanService<UnitInfo> {

    /**
     * 根据用户ID和租户编码获取机构信息
     * @param userId
     * @param tenantCode
     * @return
     */
     UnitInfo getUnitByUser(Long userId, String tenantCode);

    /**
     * 判断账号是否租户管理员
     * @param userAccount
     * @param tenantCode
     * @return
     */
     Boolean isTenantAdmin(String userAccount, String tenantCode);
}
