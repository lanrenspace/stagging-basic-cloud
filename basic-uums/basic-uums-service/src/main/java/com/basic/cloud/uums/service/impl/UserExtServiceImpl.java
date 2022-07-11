package com.basic.cloud.uums.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.uums.entity.UserExt;
import com.basic.cloud.uums.mapper.UserExtMapper;
import com.basic.cloud.uums.service.UserExtService;
import org.springframework.stereotype.Service;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class UserExtServiceImpl extends BaseBeanServiceImpl<UserExt, UserExtMapper> implements UserExtService {

    /**
     * 限制登录失败总次数
     */
    private final Integer LIMIT_LOGIN_FAIL_COUNT = 3;

    @Override
    public void updateUserLoginFails(String userAccount, boolean status) {
        UserExt userExt = getBaseMapper().queryUExtByAccount(userAccount);
        if (null == userExt) {
            logger.error("user does not exist:account:{}", userAccount);
            return;
        }
        userExt.setLoginFails(status ? 0 : userExt.getLoginFails() + 1);
        // 登录失败次数大于或等于3次时，锁定账号
        if (userExt.getLoginFails() >= LIMIT_LOGIN_FAIL_COUNT) {
            userExt.setLoginFails(0);
            userExt.setUserAccountLocked(true);
        }
        getBaseMapper().updateById(userExt);
    }
}
