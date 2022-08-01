package com.basic.cloud.message.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import org.apache.commons.lang.StringUtils;

/**
 * @Author lanrenspace@163.com
 * @Description: 阿里云短信服务工具
 **/
public class AliyunSmsUtil {

    /**
     * 短信端点
     */
    private static final String ENDPOINT = "dysmsapi.aliyuncs.com";

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws Exception
     */
    private static Client createClient(String accessKeyId, String accessKeySecret, String endpoint) throws Exception {
        Config config = new Config().setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret);
        config.endpoint = endpoint;
        return new Client(config);
    }


    /**
     * 发送短信
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param signName
     * @param templateCode
     * @param endpoint
     * @param phoneNumber
     * @return
     * @throws Exception
     */
    public static SendSmsResponse sendAli(String accessKeyId, String accessKeySecret, String signName, String templateCode, String phoneNumber, String endpoint, String reqData) throws Exception {
        if (StringUtils.isEmpty(endpoint)) {
            endpoint = ENDPOINT;
        }
        Client client = createClient(accessKeyId, accessKeySecret, endpoint);
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setTemplateParam(reqData);
        return client.sendSms(request);
    }
}
