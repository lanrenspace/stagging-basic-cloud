package com.basic.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@EnableFeignClients(value = {"com.basic.cloud"})
@ComponentScan(basePackages = {"com.basic.cloud.boot"})
public class EnabledFeignConfiguration {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(EnabledFeignConfiguration.class);

    public EnabledFeignConfiguration() {
        logger.info("EnabledFeignConfiguration.");
    }
}
