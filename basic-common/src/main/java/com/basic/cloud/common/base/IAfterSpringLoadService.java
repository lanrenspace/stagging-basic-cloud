package com.basic.cloud.common.base;

import org.springframework.core.Ordered;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public interface IAfterSpringLoadService extends Ordered {

    /**
     * 执行主体
     *
     * @throws Exception
     */
    void run() throws Exception;
}
