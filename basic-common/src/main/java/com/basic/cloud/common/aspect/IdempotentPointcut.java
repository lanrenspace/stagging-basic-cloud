package com.basic.cloud.common.aspect;

import com.basic.cloud.common.annotion.Idempotent;
import com.basic.cloud.common.idempotent.AnnotationMetaDataHolder;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class IdempotentPointcut extends StaticMethodMatcherPointcut {
    private AnnotationMetaDataHolder annotationMetaDataHolder;

    public IdempotentPointcut(AnnotationMetaDataHolder annotationMetaDataHolder) {
        this.annotationMetaDataHolder = annotationMetaDataHolder;
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        Idempotent annotation = method.getAnnotation(Idempotent.class);
        boolean match = annotation != null;
        if (match) {
            annotationMetaDataHolder.putMetaData(method, annotation);
        }
        return match;
    }
}
