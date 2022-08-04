package com.basic.cloud.uums.boot;

import com.basic.cloud.common.base.IAfterSpringLoadService;
import com.basic.cloud.uums.service.ResourceInfoService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@AllArgsConstructor
@Service
public class UumAfterSpringLoadServiceImpl implements IAfterSpringLoadService {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ResourceInfoService resourceInfoService;

    @Override
    public void run() throws Exception {
        logger.info("load resource url start...");
        this.resourceInfoService.refreshCache(null);
        logger.info("load resource url end...");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
