package com.basic.cloud.gateway.boot;

import com.basic.cloud.common.base.AutoConfigurationConditional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class GatewayAutoConfigurationConditional implements AutoConfigurationConditional {

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
