package com.basic.cloud.message.controller;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.message.service.SmsService;
import com.basic.cloud.message.vo.VarCodeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lanrenspace@163.com
 * @Description: 第三方短信服务
 **/
@RestController
@RequestMapping("/thirdSms")
public class ThirdSmsEndpoint {

    private final SmsService smsService;

    public ThirdSmsEndpoint(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @GetMapping("/sendCode")
    public ResultData<VarCodeVO> sendVerCode(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return ResultData.error("手机号不能为空!");
        }
        VarCodeVO varCodeVO = this.smsService.sendVerCode(mobile);
        return ResultData.ok(varCodeVO);
    }
}
