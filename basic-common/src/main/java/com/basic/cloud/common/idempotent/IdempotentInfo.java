package com.basic.cloud.common.idempotent;

import com.fasterxml.jackson.databind.JavaType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Getter
@AllArgsConstructor
public class IdempotentInfo {

    private final String key;

    private final Long maxLockMilli;

    private final JavaType javaType;

    private final IdempotentManager idempotentManager;
}
