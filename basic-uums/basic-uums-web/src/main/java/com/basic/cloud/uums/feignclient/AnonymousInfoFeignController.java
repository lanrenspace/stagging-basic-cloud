package com.basic.cloud.uums.feignclient;

import com.basic.cloud.uums.service.AnonymousInfoService;
import com.basic.cloud.uums.vo.AnonymousInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@RestController
@RequestMapping("/feign/anonymousInfo")
public class AnonymousInfoFeignController {

    private final AnonymousInfoService anonymousInfoService;

    public AnonymousInfoFeignController(AnonymousInfoService anonymousInfoService) {
        this.anonymousInfoService = anonymousInfoService;
    }

    /**
     * 获取所有的白名单信息
     *
     * @return
     */
    @GetMapping("/all")
    public List<AnonymousInfoVO> all() {
        return this.anonymousInfoService.getAllAnonymousInfo(null);
    }
}
