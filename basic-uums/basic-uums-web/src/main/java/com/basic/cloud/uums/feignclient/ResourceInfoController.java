package com.basic.cloud.uums.feignclient;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.service.ResourceInfoService;
import com.basic.cloud.uums.vo.ResourceUrlVO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@AllArgsConstructor
@RestController
@RequestMapping("/feign/resourceInfo")
public class ResourceInfoController {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ResourceInfoService resourceInfoService;

    /**
     * 系统初始化资源信息
     *
     * @param resourceUrlVOS
     * @return
     */
    @PostMapping("/sysInit")
    public ResultData<Void> initResourceInfo(@RequestBody List<ResourceUrlVO> resourceUrlVOS) {
        if (CollectionUtils.isEmpty(resourceUrlVOS)) {
            logger.warn("No resource information.");
            return ResultData.ok();
        }
        resourceInfoService.saveResourceUrl(resourceUrlVOS);
        return ResultData.ok();
    }
}
