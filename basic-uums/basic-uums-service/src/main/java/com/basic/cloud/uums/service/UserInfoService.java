package com.basic.cloud.uums.service;

import com.basic.cloud.common.base.IBaseBeanService;
import com.basic.cloud.common.bean.UserDetail;
import com.basic.cloud.common.vo.ResultPage;
import com.basic.cloud.uums.dto.AddUserReqQuickDto;
import com.basic.cloud.uums.dto.QueryUserInfoReqDto;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.vo.UserInfoVO;

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
     * 根据用户账号查询用户信息
     *
     * @param openId
     * @return
     */
    UserInfo queryUserByOpenId(String openId);

    /**
     * 根据用户ID获取详情
     *
     * @param userId
     * @return
     */
    UserDetail getUserDetailInfo(Long userId);

    /**
     * 快速添加用户
     * @param addUserReqQuickDto
     */
    void addUserQuick(AddUserReqQuickDto addUserReqQuickDto);

    /**
     * 查询用户列表
     * @param queryUserInfoReqDto
     * @return
     */
    ResultPage<UserInfoVO> list(QueryUserInfoReqDto queryUserInfoReqDto);
}
