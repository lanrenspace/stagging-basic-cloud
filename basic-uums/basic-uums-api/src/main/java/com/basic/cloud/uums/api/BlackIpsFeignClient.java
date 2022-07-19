package com.basic.cloud.uums.api;

import com.basic.cloud.uums.vo.BlackIpVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.uum}", path = "/feign/blackIps", contextId = "blackIpsFeignClient")
public interface BlackIpsFeignClient {

    /**
     * 获取所有的黑名单ip
     *
     * @return
     */
    @GetMapping("/all")
    List<BlackIpVO> getAllBlackIps();
}
