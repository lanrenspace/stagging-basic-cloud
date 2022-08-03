package com.basic.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@ComponentScan(basePackages = {"com.basic.cloud.boot"})
public class EnabledAbsClientConfiguration {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EnabledAbsClientConfiguration.class);

    public EnabledAbsClientConfiguration() {
        logger.info("EnabledAbsClientConfiguration.");
    }
}
