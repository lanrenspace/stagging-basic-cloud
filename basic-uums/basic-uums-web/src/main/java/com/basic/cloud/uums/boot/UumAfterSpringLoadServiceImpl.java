package com.basic.cloud.uums.boot;

import com.basic.cloud.common.base.IAfterSpringLoadService;
import com.basic.cloud.uums.service.AnonymousInfoService;
import com.basic.cloud.uums.service.BlackIpsService;
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
    private final BlackIpsService blackIpsService;
    private final AnonymousInfoService anonymousInfoService;

    @Override
    public void run() throws Exception {

        logger.info("load resource url start...");
        this.resourceInfoService.refreshCache(null);
        logger.info("load resource url end...");

        logger.info("load blackIps start...");
        this.blackIpsService.refreshCache();
        logger.info("load blackIps end...");

        logger.info("load anonymousInfo start...");
        this.anonymousInfoService.refreshCache(null);
        logger.info("load anonymousInfo end...");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
