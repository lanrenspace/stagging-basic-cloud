package com.basic.cloud.uums.feignclient;

import com.basic.cloud.uums.entity.RoleInfo;
import com.basic.cloud.uums.service.UserGroupRoleService;
import com.basic.cloud.uums.vo.RoleInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/userGroupRole")
public class UserGroupRoleController {

    private final UserGroupRoleService userGroupRoleService;

    public UserGroupRoleController(UserGroupRoleService userGroupRoleService) {
        this.userGroupRoleService = userGroupRoleService;
    }

    /**
     * 根据用户ID获取用户角色信息
     *
     * @param userId 用户ID
     */
    @GetMapping("/getRolesByUserId")
    public List<RoleInfoVO> getRolesByUserId(Long userId) {
        return userGroupRoleService.getRolesByUserId(userId);
    }
}
