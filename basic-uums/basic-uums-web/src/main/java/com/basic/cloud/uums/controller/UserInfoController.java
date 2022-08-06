package com.basic.cloud.uums.controller;

import cn.hutool.json.JSONUtil;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.common.vo.ResultPage;
import com.basic.cloud.uums.dto.AddUserReqQuickDto;
import com.basic.cloud.uums.dto.QueryUserInfoReqDto;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.service.UserInfoService;
import com.basic.cloud.uums.vo.UserInfoVO;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/userInfo")
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

    @PostMapping("/addUserQuick")
    ResultData addUserQuick(@RequestBody @Validated AddUserReqQuickDto addUserReqDto) {
        userInfoService.addUserQuick(addUserReqDto);
        return ResultData.ok("新增员工成功");
    }

    @GetMapping("/list")
    ResultPage<UserInfoVO> listUserInfo(@RequestParam("data") String data) {
        return userInfoService.list(JSONUtil.toBean(data, QueryUserInfoReqDto.class));
    }

}
