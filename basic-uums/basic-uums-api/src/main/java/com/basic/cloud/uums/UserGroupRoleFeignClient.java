package com.basic.cloud.uums;

import com.basic.cloud.uums.entity.RoleInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/userGroupRole", contextId = "userGroupRoleFeignClient")
public interface UserGroupRoleFeignClient {

    /**
     * 根据用户ID获取用户角色信息
     *
     * @param userId 用户ID
     */
    @GetMapping("/getRolesByUserId")
    List<RoleInfo> getRolesByUserId(Long userId);
}
