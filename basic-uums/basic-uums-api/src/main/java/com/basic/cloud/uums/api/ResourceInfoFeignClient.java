package com.basic.cloud.uums.api;

import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.vo.ResourceUrlVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/feign/resourceInfo", contextId = "resourceInfoFeignClient")
public interface ResourceInfoFeignClient {

    /**
     * 系统初始化资源信息
     *
     * @param resourceUrlVOS
     * @return
     */
    @PostMapping("/sysInit")
    ResultData<Void> initResourceInfo(@RequestBody List<ResourceUrlVO> resourceUrlVOS);
}
