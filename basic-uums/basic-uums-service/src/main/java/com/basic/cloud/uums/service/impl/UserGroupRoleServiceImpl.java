package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.contstant.DelEnum;
import com.basic.cloud.common.transfer.ModelMapper;
import com.basic.cloud.uums.contstant.UumConst;
import com.basic.cloud.uums.entity.RoleInfo;
import com.basic.cloud.uums.entity.UserGroupRole;
import com.basic.cloud.uums.mapper.UserGroupRoleMapper;
import com.basic.cloud.uums.service.RoleInfoService;
import com.basic.cloud.uums.service.UserGroupRoleService;
import com.basic.cloud.uums.vo.RoleInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class UserGroupRoleServiceImpl extends BaseBeanServiceImpl<UserGroupRole, UserGroupRoleMapper> implements UserGroupRoleService {

    private final RoleInfoService roleInfoService;

    public UserGroupRoleServiceImpl(RoleInfoService roleInfoService) {
        this.roleInfoService = roleInfoService;
    }

    @Override
    public List<RoleInfoVO> getRolesByUserId(Long userId) {
        List<UserGroupRole> userGroupRoles = getBaseMapper().selectList(getLambdaQueryWrapper().eq(UserGroupRole::getUserGroupType, UumConst.UserGroupType.USER)
                .eq(UserGroupRole::getUserGroupId, userId)
                .eq(BisDataEntity::getDelFlag, DelEnum.NOT_DELETED.getCode()));
        List<RoleInfoVO> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userGroupRoles)) {
            List<Long> roleIds = userGroupRoles.stream().map(UserGroupRole::getRoleId).collect(Collectors.toList());
            List<RoleInfo> roleInfos = roleInfoService.list(roleInfoService.getLambdaQueryWrapper().in(RoleInfo::getId, roleIds).eq(BisDataEntity::getDelFlag, DelEnum.NOT_DELETED.getCode()));
            roles = roleInfos.stream().map(item -> {
                RoleInfoVO roleInfoVO = new RoleInfoVO();
                roleInfoVO.setRoleId(item.getId());
                roleInfoVO.setRoleName(item.getRoleName());
                roleInfoVO.setRoleCode(item.getRoleCode());
                return roleInfoVO;
            }).collect(Collectors.toList());
        }
        return roles;
    }
}
