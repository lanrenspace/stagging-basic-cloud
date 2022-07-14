package com.basic.cloud.uaa.boot;

import com.basic.cloud.common.contstant.OrgConst;
import com.basic.cloud.uums.api.UnitFeignClient;
import com.basic.cloud.uums.api.UserGroupRoleFeignClient;
import com.basic.cloud.uums.api.UserInfoFeignClient;
import com.basic.cloud.uums.entity.UnitInfo;
import com.basic.cloud.uums.entity.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description: 用户属性参数自定义
 **/
public class SubjectAttributeUserTokenConverter extends DefaultUserAuthenticationConverter {

    private final UserInfoFeignClient userInfoFeignClient;
    private final UserGroupRoleFeignClient userGroupRoleFeignClient;
    private final UnitFeignClient unitFeignClient;

    public SubjectAttributeUserTokenConverter(UserInfoFeignClient userInfoFeignClient, UserGroupRoleFeignClient userGroupRoleFeignClient, UnitFeignClient unitFeignClient) {
        this.userInfoFeignClient = userInfoFeignClient;
        this.userGroupRoleFeignClient = userGroupRoleFeignClient;
        this.unitFeignClient = unitFeignClient;
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        /**
         * 用户信息
         */
        response.put("userAccount", authentication.getName());
        UserInfo userInfo = userInfoFeignClient.queryUserByAccount(authentication.getName());
        response.put("tenantCode", userInfo.getTenantCode());
        response.put("userId", userInfo.getId());
        response.put("userName", userInfo.getName());
        response.put("userEmail", userInfo.getEmail());
        response.put("userType", userInfo.getType());
        response.put("userMobile", userInfo.getMobile());
        /**
         * 添加角色信息
         */
        response.put("roles", userGroupRoleFeignClient.getRolesByUserId(userInfo.getId()));
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        /**
         * 组织机构信息
         */
        UnitInfo unitInfo = unitFeignClient.getUnitByUser(userInfo.getId(), userInfo.getTenantCode());
        response.put("unitId", unitInfo.getId());
        response.put("unitName", unitInfo.getUnitName());
        response.put("unitCode", unitInfo.getUnitCode());
        boolean tenantAdmin = unitFeignClient.isTenantAdmin(userInfo.getAccount(), userInfo.getTenantCode());
        response.put("tenantAdmin", tenantAdmin);
        boolean tenantAdminUnit = false;
        if (OrgConst.ADMIN_TENANT_CODE.equals(unitInfo.getTenantCode())) {
            tenantAdminUnit = true;
        }
        response.put("tenantAdminUnit", tenantAdminUnit);
        boolean admin = false;
        if (tenantAdminUnit && tenantAdmin) {
            admin = true;
        }
        response.put("admin", admin);
        return response;
    }
}
