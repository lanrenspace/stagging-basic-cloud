package com.basic.cloud.common.starter;

import com.basic.cloud.common.boot.PlatformProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@ComponentScan(basePackages = {"com.basic.cloud.common.exceptions", "com.basic.cloud.common.boot"})
@MapperScan(basePackages = "com.ywkj.uidgenerator.worker.mapper")
public class CommonAutoConfiguration {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CommonAutoConfiguration.class);

    private final PlatformProperties platformProperties;
    private final ConfigurableApplicationContext applicationContext;

    public CommonAutoConfiguration(PlatformProperties platformProperties, ConfigurableApplicationContext applicationContext) {
        this.platformProperties = platformProperties;
        this.applicationContext = applicationContext;
        initSwaggerConfig();
        logger.info("load Common comp success!");
    }

    /**
     * 初始化swagger配置
     */
    private void initSwaggerConfig() {
        List<PlatformProperties.Swagger> swaggers = platformProperties.getSwagger();
        if (!CollectionUtils.isEmpty(swaggers)) {
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) this.applicationContext.getBeanFactory();
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Docket.class);
            beanDefinitionBuilder.addConstructorArgValue(DocumentationType.SWAGGER_2);
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            swaggers.stream().filter(PlatformProperties.Swagger::isEnable).forEach(swagger -> {
                String beanName = "swaggerDocket" + RandomStringUtils.random(6);
                beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
                Docket docket = this.applicationContext.getBean(beanName, Docket.class);
                docket.groupName(swagger.getGroupName()).select().apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                        .paths(PathSelectors.any()).build();
                PlatformProperties.Swagger.ApiInfo apiInfo = swagger.getApiInfo();
                if (null != apiInfo) {
                    docket.apiInfo(new ApiInfo(apiInfo.getTitle(), apiInfo.getDescription(), apiInfo.getVersion(), "", null, apiInfo.getTitle(), "http://www.apache.org/", Collections.emptyList()));
                }
            });
        }
    }
}
