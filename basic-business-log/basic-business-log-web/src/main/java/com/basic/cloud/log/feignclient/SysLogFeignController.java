package com.basic.cloud.log.feignclient;

import com.basic.cloud.log.entity.SysLog;
import com.basic.cloud.log.service.SysLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/sysLog")
public class SysLogFeignController {

    private final SysLogService logService;

    public SysLogFeignController(SysLogService logService) {
        this.logService = logService;
    }

    /**
     * 记录日志
     *
     * @param sysLog
     */
    @PostMapping("/record")
    public void record(@RequestBody SysLog sysLog) {
        logService.save(sysLog);
    }
}
