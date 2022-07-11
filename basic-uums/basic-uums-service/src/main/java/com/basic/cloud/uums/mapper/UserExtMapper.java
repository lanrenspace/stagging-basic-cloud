package com.basic.cloud.uums.mapper;

import com.basic.cloud.common.base.BaseBeanMapper;
import com.basic.cloud.uums.entity.UserExt;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface UserExtMapper extends BaseBeanMapper<UserExt> {

    /**
     * 根据账号查询扩展信息
     *
     * @param userAccount
     * @return
     */
    UserExt queryUExtByAccount(String userAccount);
}
