package com.basic.cloud.log.api;

import com.basic.cloud.log.entity.SysLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@FeignClient(value = "${api.feign.client.log}", path = "/feign/sysLog", contextId = "sysLogFeignClient")
public interface SysLogFeignClient {

    /**
     * 记录日志
     *
     * @param sysLog
     */
    @PostMapping("/record")
    void record(SysLog sysLog);
}
