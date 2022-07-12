package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.contstant.DelEnum;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.mapper.UserInfoMapper;
import com.basic.cloud.uums.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class UserInfoServiceImpl extends BaseBeanServiceImpl<UserInfo, UserInfoMapper> implements UserInfoService {
    @Override
    public UserInfo queryUserByAccount(String userAccount) {
        Optional<UserInfo> first = getBaseMapper().selectList(getLambdaQueryWrapper().eq(UserInfo::getAccount, userAccount)
                .eq(BisDataEntity::getDelFlag, DelEnum.NOT_DELETED.getCode())).stream().findFirst();
        return first.orElse(null);
    }
}
