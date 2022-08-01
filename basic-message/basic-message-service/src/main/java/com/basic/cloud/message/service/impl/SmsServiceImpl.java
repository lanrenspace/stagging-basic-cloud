package com.basic.cloud.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.basic.cloud.common.exceptions.DataException;
import com.basic.cloud.common.exceptions.ServiceException;
import com.basic.cloud.common.utils.RandomUtil;
import com.basic.cloud.common.utils.RedisUtil;
import com.basic.cloud.message.bean.MessageProperties;
import com.basic.cloud.message.service.SmsService;
import com.basic.cloud.message.utils.AliyunSmsUtil;
import com.basic.cloud.message.vo.VarCodeVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class SmsServiceImpl implements SmsService {

    Pattern pattern = Pattern.compile("^[1]\\d{10}$");

    private MessageProperties properties;
    private RedisUtil redisUtil;

    public SmsServiceImpl(MessageProperties properties, RedisUtil redisUtil) {
        this.properties = properties;
        this.redisUtil = redisUtil;
    }

    @Override
    public VarCodeVO sendVerCode(String mobile) {
        if (ObjectUtils.isEmpty(mobile)) {
            throw new DataException("接收手机号不能为!");
        }
        boolean matches = this.pattern.matcher(mobile).matches();
        if (!matches) {
            throw new DataException("请输入合法的手机号码!");
        }
        if (redisUtil.exists(mobile)) {
            return VarCodeVO.builder().code(redisUtil.get(mobile, String.class)).second(redisUtil.getExpire(mobile)).build();
        }
        MessageProperties.ThirdSms thirdSms = properties.getThirdSms();
        String numberRandom = RandomUtil.getNumberRandom(ObjectUtils.isEmpty(thirdSms.getCodeLength()) ? MessageProperties.ThirdSms.DEFAULT_SMS_CODE_LENGTH : thirdSms.getCodeLength());
        try {
            Long codeValidSecond = ObjectUtils.isEmpty(thirdSms.getCodeValidSecond()) ? MessageProperties.ThirdSms.DEFAULT_CODE_VALID_SECOND : thirdSms.getCodeValidSecond();
            redisUtil.set(mobile, numberRandom, codeValidSecond);
            Map<String, Object> params = new HashMap<>(1);
            params.put(ObjectUtils.isEmpty(thirdSms.getCodeParamName()) ? MessageProperties.ThirdSms.DEFAULT_CODE_PARAM_NAME : thirdSms.getCodeParamName(), numberRandom);
            SendSmsResponse response = AliyunSmsUtil.sendAli(thirdSms.getAccessKeyId(), thirdSms.getAccessKeySecret(), thirdSms.getSignName(), thirdSms.getTemplateCode(), mobile, thirdSms.getEndopint(), JSONObject.toJSONString(params));
            return VarCodeVO.builder().code(numberRandom).second(codeValidSecond).build();
        } catch (Exception e) {
            redisUtil.deleteCache(mobile);
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), 500, "验证码发送失败!");
        }
    }
}
