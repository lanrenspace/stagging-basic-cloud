package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.uums.entity.UserExt;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface UserExtService extends IBaseBeanService<UserExt> {

    /**
     * 更新登录失败次数
     * @param userAccount 账号
     * @param status 是否重置
     */
    void updateUserLoginFails(String userAccount, boolean status);
}
