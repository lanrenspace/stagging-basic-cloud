package com.basic.cloud.log;

import com.basic.cloud.EnabledFeignConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@ComponentScan(basePackages = {"com.basic.cloud.log.aop"})
public class EnabledLogConfiguration {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(EnabledFeignConfiguration.class);

    public EnabledLogConfiguration() {
        logger.info("EnabledLogConfiguration.");
    }
}
