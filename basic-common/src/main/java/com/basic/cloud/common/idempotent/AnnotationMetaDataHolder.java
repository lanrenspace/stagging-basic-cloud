package com.basic.cloud.common.idempotent;

import com.basic.cloud.common.annotion.Idempotent;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description: 注解上元数据持有者
 **/
public class AnnotationMetaDataHolder {

    private final ObjectMapper CAMEL_CASE_MAPPER = new ObjectMapper();

    private Map<Method, IdempotentAnnotationMeta> hashMap = new HashMap<>();


    public synchronized void putMetaData(Method method, Idempotent idempotent) {
        MethodParameter methodParameter = new MethodParameter(method, -1);
        Type parameterType = methodParameter.getNestedGenericParameterType();
        JavaType javaType = CAMEL_CASE_MAPPER.getTypeFactory().constructType(GenericTypeResolver.resolveType(parameterType, method.getReturnType()));
        hashMap.put(method, new IdempotentAnnotationMeta(idempotent, javaType));
    }

    public IdempotentAnnotationMeta getMetaData(Method method) {
        return hashMap.get(method);
    }
}
