package com.basic.cloud.common.boot;

import com.basic.cloud.common.aspect.IdempotentAspect;
import com.basic.cloud.common.aspect.IdempotentBeanFactoryPointcutAdvisor;
import com.basic.cloud.common.aspect.IdempotentPointcut;
import com.basic.cloud.common.base.IdInjectionStrategy;
import com.basic.cloud.common.idempotent.AnnotationMetaDataHolder;
import com.basic.cloud.common.idempotent.IdempotentInfoParser;
import com.basic.cloud.common.idempotent.IdempotentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description: 幂等配置信息
 **/
@Configuration
public class IdempotentConfig {

    private final Map<String, IdempotentManager> idempotentManagerMap;

    private final Map<String, IdInjectionStrategy> keyGeneratorMap;
    private final IdempotentManager idempotentManager;

    public IdempotentConfig(Map<String, IdempotentManager> idempotentManagerMap,
                            @Autowired(required = false) Map<String, IdInjectionStrategy> keyGeneratorMap,
                            IdempotentManager idempotentManager) {
        this.idempotentManagerMap = idempotentManagerMap;
        this.keyGeneratorMap = keyGeneratorMap;
        this.idempotentManager = idempotentManager;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public IdempotentBeanFactoryPointcutAdvisor idempotentBeanFactoryPointcutAdvisor() {
        IdempotentBeanFactoryPointcutAdvisor advisor = new IdempotentBeanFactoryPointcutAdvisor();
        advisor.setPc(new IdempotentPointcut(idempotentAnnotationMetaDataHolder()));
        advisor.setOrder(Ordered.LOWEST_PRECEDENCE);
        advisor.setAdvice(new IdempotentAspect(idempotentInfoParser()));
        return advisor;
    }

    @Bean
    public AnnotationMetaDataHolder idempotentAnnotationMetaDataHolder() {
        return new AnnotationMetaDataHolder();
    }

    @Bean
    public IdempotentInfoParser idempotentInfoParser() {
        return new IdempotentInfoParser(idempotentAnnotationMetaDataHolder(), idempotentManagerMap, keyGeneratorMap, idempotentManager);
    }
}
