package com.basic.cloud.uums.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.common.contstant.DelEnum;
import com.basic.cloud.common.vo.ResultPage;
import com.basic.cloud.uums.contstant.UumConst;
import com.basic.cloud.uums.dto.AddUserReqQuickDto;
import com.basic.cloud.uums.dto.QueryUserInfoReqDto;
import com.basic.cloud.uums.entity.UnitInfo;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.mapper.UserInfoMapper;
import com.basic.cloud.uums.service.UnitInfoService;
import com.basic.cloud.uums.service.UserGroupRoleService;
import com.basic.cloud.uums.service.UserInfoService;
import com.basic.cloud.uums.vo.RoleInfoVO;
import com.basic.cloud.uums.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class UserInfoServiceImpl extends BaseBeanServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private final UserGroupRoleService userGroupRoleService;
    @Autowired
    private final UnitInfoService unitInfoService;

    @Autowired
    private   UserInfoMapper userInfoMapper;

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

    @Override
    public void addUserQuick(AddUserReqQuickDto addUserReqQuickDto) {
        // 判断是否合法的租户
        UserInfo userInfo = new UserInfo();

        // userInfo.setId(Long.parseLong(getIdStrategy().id()));
        BeanUtils.copyProperties(addUserReqQuickDto, userInfo);
        // 设置密码盐值
        // userInfo.setSlat(RandomUtil.randomNumbers(6));
        userInfo.setStatus(UumConst.UserStatus.ACTIVE);
        save(userInfo);
    }

    @Override
    public ResultPage<UserInfoVO> list(QueryUserInfoReqDto queryUserInfoReqDto) {
        ResultPage<UserInfoVO> result = new ResultPage<>();

        LambdaQueryWrapper<UserInfo> lqw = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(queryUserInfoReqDto.getName())) {
            lqw.eq(UserInfo::getName, queryUserInfoReqDto.getName());
        }
        if (!ObjectUtils.isEmpty(queryUserInfoReqDto.getMobile())) {
            lqw.eq(UserInfo::getMobile, queryUserInfoReqDto.getMobile());
        }
        if (!ObjectUtils.isEmpty(queryUserInfoReqDto.getTenantCode())) {
            lqw.eq(BisDataEntity::getTenantCode, queryUserInfoReqDto.getTenantCode());
        }

        Page<UserInfo> page = userInfoMapper.selectPage(new Page<>(queryUserInfoReqDto.getPageNumber(), queryUserInfoReqDto.getPageSize()),lqw);

        CopyOptions copyOptions = CopyOptions.create();
        result.setTotal(page.getTotal());
        result.setRows(BeanUtil.copyToList(page.getRecords(), UserInfoVO.class, copyOptions));

        return result;
    }
}
