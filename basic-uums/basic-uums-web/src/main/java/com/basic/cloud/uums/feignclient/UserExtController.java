package com.basic.cloud.uums.feignclient;

import com.basic.cloud.uums.service.UserExtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/userExt")
public class UserExtController {

    private final UserExtService userExtService;

    public UserExtController(UserExtService userExtService) {
        this.userExtService = userExtService;
    }

    /**
     * 更新登录失败次数
     *
     * @param userAccount 账号
     * @param status      是否重置
     */
    @PostMapping("/uploadLoginFails")
    public void updateUserLoginFails(String userAccount, boolean status) {
        userExtService.updateUserLoginFails(userAccount, status);
    }
}
