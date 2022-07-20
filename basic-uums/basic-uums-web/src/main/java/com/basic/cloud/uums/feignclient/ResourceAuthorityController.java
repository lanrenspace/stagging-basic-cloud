package com.basic.cloud.uums.feignclient;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.service.ResourceAuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/resourceAuthority")
public class ResourceAuthorityController {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ResourceAuthorityController.class);
    private final ResourceAuthorityService resourceAuthorityService;

    public ResourceAuthorityController(ResourceAuthorityService resourceAuthorityService) {
        this.resourceAuthorityService = resourceAuthorityService;
    }

    /**
     * url 资源验权
     *
     * @param userId
     * @param url
     * @param method
     * @return
     */
    @GetMapping("/permission")
    public ResultData<Boolean> permission(Long userId, String url, String method) {
        if (ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(url) || ObjectUtils.isEmpty(method)) {
            return ResultData.ok(false);
        }
        boolean decide = resourceAuthorityService.decide(userId, url, method);
        logger.info("Resource permission. userId:{}, url:{}, method:{}, permission:{}", userId, url, method, decide);
        return ResultData.ok(decide);
    }
}
