package com.basic.cloud.uums;

import com.basic.cloud.uums.entity.UnitInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/feign/unitInfo", contextId = "unitFeignClient")
public interface UnitFeignClient {

    /**
     * 获取用户组织信息
     *
     * @param userId     用户ID
     * @param tenantCode 租户编码
     */
    @GetMapping("/getUnitByUser")
    UnitInfo getUnitByUser(@RequestParam("userId") Long userId, @RequestParam("tenantCode") String tenantCode);

    /**
     * 判断用户是否组织管理员
     *
     * @param userAccount
     * @param tenantCode
     * @return
     */
    @GetMapping("/isTenantAdmin")
    Boolean isTenantAdmin(@RequestParam("userAccount") String userAccount, @RequestParam("tenantCode") String tenantCode);
}
