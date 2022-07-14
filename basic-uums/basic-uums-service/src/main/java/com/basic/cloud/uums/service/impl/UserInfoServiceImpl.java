package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.common.contstant.DelEnum;
import com.basic.cloud.uums.entity.UnitInfo;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.mapper.UserInfoMapper;
import com.basic.cloud.uums.service.UnitInfoService;
import com.basic.cloud.uums.service.UserGroupRoleService;
import com.basic.cloud.uums.service.UserInfoService;
import com.basic.cloud.uums.vo.RoleInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class UserInfoServiceImpl extends BaseBeanServiceImpl<UserInfo, UserInfoMapper> implements UserInfoService {

    private final UserGroupRoleService userGroupRoleService;
    private final UnitInfoService unitInfoService;

    public UserInfoServiceImpl(UserGroupRoleService userGroupRoleService, UnitInfoService unitInfoService) {
        this.userGroupRoleService = userGroupRoleService;
        this.unitInfoService = unitInfoService;
    }

    @Override
    public UserInfo queryUserByAccount(String userAccount) {
        Optional<UserInfo> first = getBaseMapper().selectList(getLambdaQueryWrapper().eq(UserInfo::getAccount, userAccount)
                .eq(BisDataEntity::getDelFlag, DelEnum.NOT_DELETED.getCode())).stream().findFirst();
        return first.orElse(null);
    }

    @Override
    public UserDetail getUserDetailInfo(Long userId) {
        UserInfo userInfo = getById(userId);
        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(userInfo, userDetail);
        userDetail.setUserId(userInfo.getId());

        List<RoleInfoVO> roleInfoVOS = userGroupRoleService.getRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(roleInfoVOS)) {
            List<UserDetail.RoleInfo> roleInfos = roleInfoVOS.stream().map(roleInfoVO -> {
                UserDetail.RoleInfo roleInfo = new UserDetail.RoleInfo();
                roleInfo.setId(roleInfoVO.getRoleId());
                roleInfo.setRoleCode(roleInfoVO.getRoleCode());
                roleInfo.setRoleName(roleInfoVO.getRoleName());
                return roleInfo;
            }).collect(Collectors.toList());
            userDetail.setRoleInfos(roleInfos);
        }
        UnitInfo unitInfo = unitInfoService.getUnitByUser(userInfo.getId(), userInfo.getTenantCode());
        if (unitInfo != null) {
            UserDetail.UnitInfo detailUnit = new UserDetail.UnitInfo();
            detailUnit.setId(unitInfo.getId());
            detailUnit.setUnitCode(unitInfo.getUnitCode());
            detailUnit.setUnitName(unitInfo.getUnitName());
            detailUnit.setUnitFullName(unitInfo.getUnitFullName());
            detailUnit.setUnitType(unitInfo.getUnitType());
            userDetail.setUnitInfos(Collections.singletonList(detailUnit));
        }
        return userDetail;
    }
}
