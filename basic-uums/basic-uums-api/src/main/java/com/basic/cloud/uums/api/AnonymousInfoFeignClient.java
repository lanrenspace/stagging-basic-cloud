package com.basic.cloud.uums.api;

import com.basic.cloud.uums.vo.AnonymousInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/feign/anonymousInfo", contextId = "anonymousInfoFeignClient")
public interface AnonymousInfoFeignClient {

    /**
     * 获取所有的白名单信息
     *
     * @return
     */
    @GetMapping("/all")
    List<AnonymousInfoVO> all();
}
