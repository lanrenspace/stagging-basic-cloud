package com.basic.cloud.uums.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/feign/userExt",contextId = "userExtFeignClient")
public interface UserExtFeignClient {

    /**
     * 更新登录失败次数
     *
     * @param userAccount 账号
     * @param status      是否重置
     */
    @PostMapping("/uploadLoginFails")
    void updateUserLoginFails(@RequestParam("userAccount") String userAccount,@RequestParam("status") boolean status);
}
