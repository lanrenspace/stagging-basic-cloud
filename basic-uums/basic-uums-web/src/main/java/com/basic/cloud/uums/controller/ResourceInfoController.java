package com.basic.cloud.uums.controller;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.service.ResourceInfoService;
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
@RequestMapping("/resourceInfo")
public class ResourceInfoController {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ResourceInfoService resourceInfoService;


    /**
     * 刷新资源URL缓存
     *
     * @param serviceName
     * @return
     */
    @GetMapping("/refreshCache")
    public ResultData<Void> refreshCache(String serviceName) {
        logger.info("refresh resource url start...");
        this.resourceInfoService.refreshCache(serviceName);
        logger.info("refresh resource url end...");
        return ResultData.ok();
    }
}
