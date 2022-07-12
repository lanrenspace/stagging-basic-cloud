package com.basic.cloud.uums.feignclient;

import com.basic.cloud.uums.entity.UnitInfo;
import com.basic.cloud.uums.service.UnitInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/unitInfo")
public class UnitController {

    private final UnitInfoService unitInfoService;

    public UnitController(UnitInfoService unitInfoService) {
        this.unitInfoService = unitInfoService;
    }

    /**
     * 获取用户组织信息
     *
     * @param userId     用户ID
     * @param tenantCode 租户编码
     */
    @GetMapping("/getUnitByUser")
    public UnitInfo getUnitByUser(Long userId, String tenantCode) {
        return unitInfoService.getUnitByUser(userId, tenantCode);
    }

    /**
     * 判断用户是否组织管理员
     *
     * @param userAccount
     * @param tenantCode
     * @return
     */
    @GetMapping("/isTenantAdmin")
    public Boolean isTenantAdmin(String userAccount, String tenantCode) {
        return unitInfoService.isTenantAdmin(userAccount, tenantCode);
    }
}
