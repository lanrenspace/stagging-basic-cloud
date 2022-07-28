package com.basic.cloud.common.idempotent;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Getter
@AllArgsConstructor
public class IdempotentExpressionRootObject {

    private final Object[] args;

    private final Method method;

    private final Object target;

    private final Class targetClass;

    public String getMethodName() {
        return method.getName();
    }

    public String getTargetClassName() {
        return targetClass.getName();
    }
}
