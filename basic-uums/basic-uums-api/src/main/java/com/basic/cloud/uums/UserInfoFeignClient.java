package com.basic.cloud.uums;

import com.basic.cloud.uums.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/userInfo", contextId = "userInfoFeignClient")
public interface UserInfoFeignClient {

    /**
     * 根据用户账号查询用户信息
     *
     * @param userAccount 账号
     */
    @GetMapping("/queryUserByAccount")
    UserInfo queryUserByAccount(String userAccount);
}
