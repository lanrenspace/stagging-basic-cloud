package com.basic.cloud.message.bean;

import lombok.Data;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Data
public class MessageProperties {

    /**
     * 第三方短信配置信息
     */
    private ThirdSms thirdSms = new ThirdSms();


    /**
     * 三方短信配置
     */
    @Data
    public static class ThirdSms {

        /**
         * 短信验证码默认长度
         */
        public final static Integer DEFAULT_SMS_CODE_LENGTH = 6;

        /**
         * 验证码默认有效时长
         */
        public final static Long DEFAULT_CODE_VALID_SECOND = 60L;

        /**
         * 默认code 参数名
         */
        public final static String DEFAULT_CODE_PARAM_NAME = "code";

        /**
         * accessKey
         */
        private String accessKeyId;

        /**
         * accessKeySecret
         */
        private String accessKeySecret;

        /**
         * signName
         */
        private String signName;

        /**
         * templateCode
         */
        private String templateCode;

        /**
         * 服务端点
         */
        private String endopint;

        /**
         * 验证码长度
         */
        private Integer codeLength = DEFAULT_SMS_CODE_LENGTH;

        /**
         * 验证码有效时长
         */
        private Long codeValidSecond = DEFAULT_CODE_VALID_SECOND;

        /**
         * code参数名
         */
        private String codeParamName = DEFAULT_CODE_PARAM_NAME;
    }
}
