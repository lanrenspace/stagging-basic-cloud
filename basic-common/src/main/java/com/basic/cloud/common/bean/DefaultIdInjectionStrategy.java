package com.basic.cloud.common.bean;

import com.basic.cloud.common.base.IdInjectionStrategy;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class DefaultIdInjectionStrategy implements IdInjectionStrategy {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultIdInjectionStrategy.class);

    /**
     * 时间偏移量
     */
    public static final long EPOCH = 1506503904607L;
    /**
     * 序列号bits
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 工作进程ID bits
     */
    private static final long WORKER_ID_BITS = 10L;
    /**
     * 自增量序列（最大值）
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 工作进程ID左移 bits（位数）
     */
    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;
    /**
     * 时间戳左移bits（位数）
     */
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;
    /**
     * 工作进程ID最大值
     */
    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;
    /**
     * 工作进程ID
     */
    private static long workerId;
    /**
     * 最后自增量
     */
    private long sequence;
    /**
     * 最后生成编号时间戳，单位：毫秒
     */
    private volatile long lastTime = -1L;


    @Override
    public synchronized Object id() {
        // 保证当前时间大于最后时间。时间回退会导致产生重复id
        long currentMillis = getCurrentMillis();
        Preconditions.checkState(lastTime <= currentMillis, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", lastTime, currentMillis);
        // 获取序列号
        if (lastTime == currentMillis) {
            // 当获得序号超过最大值时，归0，并去获得新的时间
            if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0;
        }
        // 设置最后时间戳
        lastTime = currentMillis;

        // 生成编号
        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    /**
     * 不停获得时间，直到大于最后时间
     * dongChen
     *
     * @param lastTime
     * @return
     */
    private long waitUntilNextTime(final long lastTime) {
        long time = getCurrentMillis();
        while (time <= lastTime) {
            time = getCurrentMillis();
        }
        return time;
    }


    public long getCurrentMillis() {
        return System.currentTimeMillis();
    }
}
