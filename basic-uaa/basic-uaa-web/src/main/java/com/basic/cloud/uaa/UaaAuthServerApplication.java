package com.basic.cloud.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@EnableFeignClients(value = {"com.basic.cloud"})
@EnableDiscoveryClient
@SpringBootApplication
public class UaaAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaAuthServerApplication.class, args);
    }
}
