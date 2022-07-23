package com.basic.cloud.common.starter;

import com.basic.cloud.common.base.AutoConfigurationConditional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@ConditionalOnMissingBean(value = {AutoConfigurationConditional.class})
@Import({Swagger2DocumentationConfiguration.class})
public class SwaggerAutoConfiguration {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SwaggerAutoConfiguration() {
        logger.info("load SwaggerAutoConfiguration.");
    }
}
