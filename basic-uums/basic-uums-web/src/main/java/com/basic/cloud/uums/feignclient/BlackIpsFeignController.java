package com.basic.cloud.uums.feignclient;

import com.basic.cloud.uums.service.BlackIpsService;
import com.basic.cloud.uums.vo.BlackIpVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/blackIps")
public class BlackIpsFeignController {

    private final BlackIpsService blackIpsService;

    public BlackIpsFeignController(BlackIpsService blackIpsService) {
        this.blackIpsService = blackIpsService;
    }

    /**
     * 获取所有的黑名单ip
     *
     * @return
     */
    @GetMapping("/all")
    public List<BlackIpVO> getAllBlackIps() {
        return blackIpsService.getAllBlackIp();
    }
}
