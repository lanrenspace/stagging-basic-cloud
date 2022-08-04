package com.basic.cloud.uums.controller;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.service.AnonymousInfoService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@AllArgsConstructor
@RestController
@RequestMapping("/anonymousInfo")
public class AnonymousInfoController {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(AnonymousInfoController.class);

    private final AnonymousInfoService anonymousInfoService;

    /**
     * 刷新缓存
     *
     * @param serviceName
     * @return
     */
    @GetMapping("/refreshCache")
    public ResultData<Void> refreshCache(String serviceName) {
        logger.info("refresh anonymousInfo start...");
        this.anonymousInfoService.refreshCache(serviceName);
        logger.info("refresh anonymousInfo end...");
        return ResultData.ok();
    }
}
