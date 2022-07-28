package com.basic.cloud.common.aspect;

import com.basic.cloud.common.idempotent.IdempotentInfo;
import com.basic.cloud.common.idempotent.IdempotentInfoParser;
import com.basic.cloud.common.idempotent.IdempotentManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.Assert;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class IdempotentAspect implements MethodInterceptor {
    private IdempotentInfoParser idempotentKeyParser;

    public IdempotentAspect(IdempotentInfoParser idempotentKeyParser) {
        Assert.notNull(idempotentKeyParser, "idempotentKeyParser 不能为空");
        this.idempotentKeyParser = idempotentKeyParser;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        IdempotentInfo idempotentInfo = idempotentKeyParser.parseIdempotentInfo(methodInvocation);
        IdempotentManager idempotentManager = idempotentInfo.getIdempotentManager();
        boolean lock = idempotentManager.tryLock(idempotentInfo);
        if (!lock) {
            return idempotentManager.handlerNoLock(idempotentInfo);
        }
        Object proceed = null;
        try {
            proceed = methodInvocation.proceed();
        } finally {
            idempotentManager.complete(idempotentInfo, proceed);
        }
        return proceed;
    }
}
