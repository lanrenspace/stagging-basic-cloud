package com.basic.cloud.common.idempotent;

import com.basic.cloud.common.base.IdInjectionStrategy;
import com.basic.cloud.common.exceptions.IdempotentException;
import com.basic.cloud.common.expression.CommonCachedExpressionEvaluator;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class IdempotentInfoParser extends CommonCachedExpressionEvaluator {

    private AnnotationMetaDataHolder annotationMetaDataHolder;

    private Map<String, IdempotentManager> idempotentManagerMap;

    private Map<String, IdInjectionStrategy> keyGeneratorMap;

    private IdempotentManager idempotentManager;


    public IdempotentInfoParser(AnnotationMetaDataHolder annotationMetaDataHolder,
                                Map<String, IdempotentManager> idempotentManagerMap,
                                Map<String, IdInjectionStrategy> keyGeneratorMap,
                                IdempotentManager idempotentManager) {
        this.annotationMetaDataHolder = annotationMetaDataHolder;
        this.idempotentManagerMap = idempotentManagerMap;
        this.keyGeneratorMap = keyGeneratorMap;
        this.idempotentManager = idempotentManager;
    }

    /**
     * 解析幂等key
     *
     * @param methodInvocation
     * @return
     */
    public IdempotentInfo parseIdempotentInfo(MethodInvocation methodInvocation) {
        Method method = methodInvocation.getMethod();
        Object target = methodInvocation.getThis();
        Object[] arguments = methodInvocation.getArguments();
        IdempotentAnnotationMeta metaData = annotationMetaDataHolder.getMetaData(method);
        String key = evaluatorKey(metaData.getKey(), target, method, arguments);
        IdInjectionStrategy keyGenerator = null;
        if (StringUtils.isNotBlank(metaData.getGenerator()) && (keyGenerator = getKeyGenerator(metaData.getGenerator())) != null) {
            key = keyGenerator.id();
        }

        IdempotentManager manager = idempotentManager;
        if (StringUtils.isNotBlank(metaData.getIdempotentManager())) {
            manager = getIdempotentManager(metaData.getIdempotentManager());
        }

        return new IdempotentInfo(key, metaData.getMaxLockMilli(), metaData.getReturnType(), manager);
    }

    private String evaluatorKey(String keyInAnnotation, Object target, Method method, Object[] args) {
        Class<?> clazz = target.getClass();
        if (StringUtils.isBlank(keyInAnnotation)) {
            return clazz.getName() + "_" + method.getName();
        }
        return evaluatorExpressionStr(keyInAnnotation, target, clazz, method, args);
    }


    @Override
    protected Object getRootObject(Object target, Class clazz, Method method, Object[] args) {
        return new IdempotentExpressionRootObject(args, method, target, clazz);
    }


    private IdInjectionStrategy getKeyGenerator(String name) {
        return Optional.ofNullable(keyGeneratorMap)
                .map(m -> m.get(name))
                .orElseThrow(() -> new IdempotentException("BeanName: " + name + " 对应的幂等Key生成器不存在"));
    }

    private IdempotentManager getIdempotentManager(String name) {
        return Optional.ofNullable(idempotentManagerMap.get(name))
                .orElseThrow(() -> new IdempotentException("BeanName: " + name + " 对应的幂等管理器不存在"));

    }

}
