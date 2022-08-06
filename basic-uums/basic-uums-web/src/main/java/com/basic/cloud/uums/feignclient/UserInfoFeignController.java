package com.basic.cloud.uums.feignclient;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.common.vo.ResultPage;
import com.basic.cloud.uums.dto.AddUserReqQuickDto;
import com.basic.cloud.uums.dto.QueryUserInfoReqDto;
import com.basic.cloud.uums.dto.UserOpenIdBindReqDto;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.service.UserInfoService;
import com.basic.cloud.uums.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/userInfo")
public class UserInfoFeignController {

    @Autowired
    private final UserInfoService userInfoService;

    public UserInfoFeignController(UserInfoService userInfoService) {
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

    @PutMapping("/bindOpenId")
    ResultData bindOpenId(@RequestBody UserOpenIdBindReqDto userOpenIdBindReqDto) {
        LambdaQueryWrapper<UserInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserInfo::getMobile, userOpenIdBindReqDto.getMobile());

        List<UserInfo> list = userInfoService.list(lqw);
        if (CollectionUtils.isEmpty(list)) {
            return ResultData.error("手机号："+userOpenIdBindReqDto.getMobile() +"找不到对应的用户", HttpStatus.CONTINUE.INTERNAL_SERVER_ERROR);
        }

        if (list.size() > 1) {
            return ResultData.error("手机号："+userOpenIdBindReqDto.getMobile() + "找到多个对应的用户", HttpStatus.CONTINUE.INTERNAL_SERVER_ERROR);
        }

        UserInfo userInfo = list.get(0);
        userInfo.setWxOpenId(userOpenIdBindReqDto.getOpenId());
        userInfoService.updateById(userInfo);
        return ResultData.ok(userInfo);
    }

    @GetMapping("/getByOpenId/{openId}")
    ResultData getByOpenId(@PathVariable("openId") String openid) {
        LambdaQueryWrapper<UserInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserInfo::getWxOpenId, openid);

        List<UserInfo> list = userInfoService.list(lqw);
        if (CollectionUtils.isEmpty(list)) {
            return ResultData.error("微信："+openid +"找不到对应的用户", HttpStatus.CONTINUE.INTERNAL_SERVER_ERROR);
        }

        if (list.size() > 1) {
            return ResultData.error("微信："+openid + "找到多个对应的用户", HttpStatus.CONTINUE.INTERNAL_SERVER_ERROR);
        }

        return ResultData.ok(list.get(0));
    }

}
