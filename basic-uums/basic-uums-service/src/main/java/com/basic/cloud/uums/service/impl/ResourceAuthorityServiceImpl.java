package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.uums.entity.ResourceAuthority;
import com.basic.cloud.uums.mapper.ResourceAuthorityMapper;
import com.basic.cloud.uums.service.ResourceAuthorityService;
import com.basic.cloud.uums.service.ResourceInfoService;
import com.basic.cloud.uums.service.UserGroupRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class ResourceAuthorityServiceImpl extends BaseBeanServiceImpl<ResourceAuthorityMapper, ResourceAuthority> implements ResourceAuthorityService {

    private final UserGroupRoleService userGroupRoleService;
    private final ResourceInfoService resourceInfoService;

    public ResourceAuthorityServiceImpl(UserGroupRoleService userGroupRoleService, ResourceInfoService resourceInfoService) {
        this.userGroupRoleService = userGroupRoleService;
        this.resourceInfoService = resourceInfoService;
    }

    @Override
    public boolean decide(Long userId, String url, String method) {
        logger.info("Resource Access user:{}, url:{}, method:{}", userId, url, method);
        // 用户拥有的角色
        List<Long> roleIds = userGroupRoleService.getRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }
        List<ResourceAuthority> resourceAuthorities = list(getLambdaQueryWrapper().in(ResourceAuthority::getRoleId, roleIds).eq(BisDataEntity::getDelFlag, false));
        if (CollectionUtils.isEmpty(resourceAuthorities)) {
            return false;
        }
        List<Long> resourceIds = resourceAuthorities.stream().map(ResourceAuthority::getResourceId).collect(Collectors.toList());
        return resourceInfoService.matchUserResources(resourceIds, url, method);
    }
}
