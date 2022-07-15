package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.contstant.DelEnum;
import com.basic.cloud.common.exceptions.DataException;
import com.basic.cloud.uums.entity.UnitInfo;
import com.basic.cloud.uums.entity.UserUnit;
import com.basic.cloud.uums.mapper.UnitInfoMapper;
import com.basic.cloud.uums.service.UnitInfoService;
import com.basic.cloud.uums.service.UserUnitService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class UnitInfoServiceImpl extends BaseBeanServiceImpl<UnitInfoMapper, UnitInfo> implements UnitInfoService {

    private final UserUnitService userUnitService;

    public UnitInfoServiceImpl(UserUnitService userUnitService) {
        this.userUnitService = userUnitService;
    }

    @Override
    public UnitInfo getUnitByUser(Long userId, String tenantCode) {
        Optional<UserUnit> first = userUnitService.list(userUnitService.getLambdaQueryWrapper().eq(UserUnit::getUserId, userId)
                .eq(UserUnit::getMain, true)
                .eq(UserUnit::getValid, true)
                .eq(BisDataEntity::getTenantCode, tenantCode)
                .eq(BisDataEntity::getDelFlag, DelEnum.NOT_DELETED.getCode())).stream().findFirst();
        if (first.isPresent()) {
            return getBaseMapper().selectById(first.get().getUnitId());
        } else {
            throw new DataException("用户未配置主组织机构信息!");
        }
    }

    @Override
    public Boolean isTenantAdmin(String userAccount, String tenantCode) {
        if (ObjectUtils.isEmpty(userAccount) || ObjectUtils.isEmpty(tenantCode)) {
            return false;
        }
        Optional<UnitInfo> first = getBaseMapper().selectList(getLambdaQueryWrapper().eq(UnitInfo::getAdminAccount, userAccount)
                .eq(BisDataEntity::getTenantCode, tenantCode)).stream().findFirst();
        return first.isPresent();
    }
}
