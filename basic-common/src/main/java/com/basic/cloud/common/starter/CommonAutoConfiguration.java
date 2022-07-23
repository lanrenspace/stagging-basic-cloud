package com.basic.cloud.common.starter;

import com.basic.cloud.common.base.AutoConfigurationConditional;
import com.basic.cloud.common.base.IdInjectionStrategy;
import com.basic.cloud.common.bean.DefaultIdInjectionStrategy;
import com.basic.cloud.common.boot.AfterSpringLoad;
import com.basic.cloud.common.boot.PlatformProperties;
import com.basic.cloud.common.boot.UserSetting;
import com.basic.cloud.common.utils.AppContextHelper;
import com.basic.cloud.common.utils.RedisUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
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
public class CommonAutoConfiguration {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CommonAutoConfiguration.class);

    private final PlatformProperties platformProperties;
    private final ConfigurableApplicationContext applicationContext;

    private final RedisTemplate<String, Object> redisTemplate;

    public CommonAutoConfiguration(PlatformProperties platformProperties, ConfigurableApplicationContext applicationContext, RedisTemplate<String, Object> redisTemplate) {
        this.platformProperties = platformProperties;
        this.applicationContext = applicationContext;
        this.redisTemplate = redisTemplate;
//        initSwaggerConfig();
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
            swaggers.forEach(swagger -> {
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

    /**
     * 全局上下文对象
     *
     * @return
     */
    @Bean("getApplicationContext")
    public AppContextHelper appContextHelper() {
        return new AppContextHelper();
    }


    /**
     * 默认id策略实现
     *
     * @return
     */
    @Bean
    public IdInjectionStrategy defaultIdInjectionStrategy() {
        return new DefaultIdInjectionStrategy();
    }

    @Bean
    public RedisUtil redisUtil() {
        return new RedisUtil(redisTemplate);
    }

    /**
     * 授权用户操作工具
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = {AutoConfigurationConditional.class})
    public UserSetting userSetting() {
        return new UserSetting(redisUtil());
    }

    @Bean
    public AfterSpringLoad afterSpringLoad() {
        return new AfterSpringLoad();
    }
}
