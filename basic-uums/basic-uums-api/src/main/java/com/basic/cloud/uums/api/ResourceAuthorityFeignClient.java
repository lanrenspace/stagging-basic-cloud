package com.basic.cloud.uums.api;

import com.basic.cloud.common.vo.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/feign/resourceAuthority", contextId = "resourceAuthorityFeignClient")
public interface ResourceAuthorityFeignClient {

    /**
     * url 资源验权
     *
     * @param userId
     * @param url
     * @param method
     * @return
     */
    @GetMapping("/permission")
    ResultData<Boolean> permission(@RequestParam("userId") Long userId, @RequestParam("url") String url, @RequestParam("method") String method);
}
