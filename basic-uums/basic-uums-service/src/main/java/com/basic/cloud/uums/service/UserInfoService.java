package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.uums.entity.UserInfo;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface UserInfoService extends IBaseBeanService<UserInfo> {

    /**
     * 根据用户账号查询用户信息
     *
     * @param userAccount
     * @return
     */
    UserInfo queryUserByAccount(String userAccount);

    /**
     * 根据用户ID获取详情
     *
     * @param userId
     * @return
     */
    UserDetail getUserDetailInfo(Long userId);
}
