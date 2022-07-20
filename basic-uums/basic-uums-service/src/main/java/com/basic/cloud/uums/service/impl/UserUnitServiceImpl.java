package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.uums.entity.UserUnit;
import com.basic.cloud.uums.mapper.UserUnitMapper;
import com.basic.cloud.uums.service.UserUnitService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class UserUnitServiceImpl extends BaseBeanServiceImpl<UserUnitMapper, UserUnit> implements UserUnitService {
    @Override
    public List<Long> getUnitIdByUserId(Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            logger.warn("param userId is null.");
            return null;
        }
        List<UserUnit> userUnits = list(getLambdaQueryWrapper().eq(UserUnit::getUserId, userId).eq(BisDataEntity::getDelFlag, false)
                .eq(UserUnit::getValid, true));
        if (!CollectionUtils.isEmpty(userUnits)) {
            return userUnits.stream().map(UserUnit::getUnitId).collect(Collectors.toList());
        }
        return null;
    }
}
