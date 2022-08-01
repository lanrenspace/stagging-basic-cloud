package com.basic.cloud.message.service;

import com.basic.cloud.message.vo.VarCodeVO;

/**
 * @Author lanrenspace@163.com
 * @Description: 短信验证码
 **/
public interface SmsService {

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    VarCodeVO sendVerCode(String mobile);
}
