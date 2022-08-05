package com.basic.cloud.gateway.service.impl;

import com.basic.cloud.common.boot.PlatformProperties;
import com.basic.cloud.common.utils.AppContextHelper;
import com.basic.cloud.gateway.service.PlatformService;
import com.basic.cloud.uums.api.ResourceInfoFeignClient;
import com.basic.cloud.uums.vo.ResourceUrlVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class PlatformServiceImpl implements PlatformService {

    private final ResourceInfoFeignClient resourceInfoFeignClient;

    public PlatformServiceImpl(ResourceInfoFeignClient resourceInfoFeignClient) {
        this.resourceInfoFeignClient = resourceInfoFeignClient;
    }

    @Override
    public boolean isAllowedResUrlInit() {
        return PlatformProperties.ActiveEnv.DEV.equals(AppContextHelper.getActiveProfile())
                || PlatformProperties.ActiveEnv.TEST.equals(AppContextHelper.getActiveProfile());
    }

    @Override
    public void resUrlInitRemote(List<ResourceUrlVO> resourceUrlVOS) {
        if (!CollectionUtils.isEmpty(resourceUrlVOS)) {
            this.resourceInfoFeignClient.initResourceInfo(resourceUrlVOS);
        }
    }
}
