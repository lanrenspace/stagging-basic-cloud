package com.basic.cloud.message.boot;

import com.basic.cloud.message.bean.MessageProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
public class MessageConfiguration {

    /**
     * 消息服务配置注册
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "platform")
    public MessageProperties messageProperties() {
        return new MessageProperties();
    }
}
