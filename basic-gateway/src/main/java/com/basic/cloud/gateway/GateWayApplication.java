package com.basic.cloud.gateway;

import com.basic.cloud.common.starter.SwaggerAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@EnableFeignClients(value = {"com.basic.cloud"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SwaggerAutoConfiguration.class})
@EnableDiscoveryClient
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
