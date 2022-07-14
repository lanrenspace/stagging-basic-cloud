package com.basic.cloud.uums.api;

import com.basic.cloud.uums.entity.RoleInfo;
import com.basic.cloud.uums.vo.RoleInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/feign/userGroupRole", contextId = "userGroupRoleFeignClient")
public interface UserGroupRoleFeignClient {

    /**
     * 根据用户ID获取用户角色信息
     *
     * @param userId 用户ID
     */
    @GetMapping("/getRolesByUserId")
    List<RoleInfoVO> getRolesByUserId(@RequestParam("userId") Long userId);
}
