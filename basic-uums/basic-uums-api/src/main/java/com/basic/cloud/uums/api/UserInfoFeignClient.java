package com.basic.cloud.uums.api;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.common.vo.ResultPage;
import com.basic.cloud.uums.dto.AddUserReqQuickDto;
import com.basic.cloud.uums.entity.UserInfo;
import com.basic.cloud.uums.vo.UserInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/feign/userInfo", contextId = "userInfoFeignClient")
public interface UserInfoFeignClient {

    /**
     * 根据用户账号查询用户信息
     *
     * @param userAccount 账号
     */
    @GetMapping("/queryUserByAccount")
    UserInfo queryUserByAccount(@RequestParam("userAccount") String userAccount);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUserDetailInfo")
    ResultData getUserDetailInfo(@RequestParam("userId") Long userId);

    @PostMapping("/addUserQuick")
    ResultData addUserQuick(@RequestBody @Validated AddUserReqQuickDto addUserReqDto);

    @GetMapping("/list")
    ResultPage<UserInfoVO> listUserInfo(@RequestParam("data") String jsonData);

}
