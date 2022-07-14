package com.basic.cloud.uums.feignclient;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.service.UserInfoService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/userInfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 根据用户账号查询用户信息
     *
     * @param userAccount 账号
     */
    @GetMapping("/queryUserByAccount")
    public UserInfo queryUserByAccount(String userAccount) {
        return userInfoService.queryUserByAccount(userAccount);
    }


    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUserDetailInfo")
    public ResultData getUserDetailInfo(Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            return null;
        }
        return ResultData.ok(userInfoService.getUserDetailInfo(userId));
    }
}
