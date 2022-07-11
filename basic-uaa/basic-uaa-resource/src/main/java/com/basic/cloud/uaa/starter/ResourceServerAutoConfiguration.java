package com.basic.cloud.uaa.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@ComponentScan(basePackages = {"com.basic.cloud.uaa.boot"})
public class ResourceServerAutoConfiguration {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(ResourceServerAutoConfiguration.class);

    public ResourceServerAutoConfiguration() {
        logger.info("loading resource server comp");
    }

}
