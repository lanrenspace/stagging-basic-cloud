package com.basic.cloud.common.idempotent;

/**
 * @Author lanrenspace@163.com
 * @Description:幂等管理器
 **/
public interface IdempotentManager {

    /**
     * 抢占锁
     *
     * @param idempotentInfo
     */
    boolean tryLock(IdempotentInfo idempotentInfo);

    /**
     * 没有抢占到锁的处理逻辑
     *
     * @param idempotentInfo
     * @return
     */
    Object handlerNoLock(IdempotentInfo idempotentInfo);

    /**
     * 占用到锁,并业务处理完成
     *
     * @param idempotentInfo
     * @param result
     */
    void complete(IdempotentInfo idempotentInfo, Object result);
}
