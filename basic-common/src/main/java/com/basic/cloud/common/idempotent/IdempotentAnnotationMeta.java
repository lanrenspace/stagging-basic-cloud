package com.basic.cloud.common.idempotent;

import com.basic.cloud.common.annotion.Idempotent;
import com.fasterxml.jackson.databind.JavaType;
import lombok.Getter;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Getter
public class IdempotentAnnotationMeta {

    private final String key;

    private final Long maxLockMilli;

    private final String generator;

    private final JavaType returnType;

    private final String idempotentManager;

    public IdempotentAnnotationMeta(Idempotent idempotent, JavaType javaType) {
        key = idempotent.key();
        maxLockMilli = idempotent.maxLockMilli();
        generator = idempotent.generator();
        idempotentManager = idempotent.idempotentManager();
        returnType = javaType;
    }

}
