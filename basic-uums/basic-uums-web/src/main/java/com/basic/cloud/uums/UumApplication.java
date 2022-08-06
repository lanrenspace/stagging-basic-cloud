package com.basic.cloud.uums;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.basic.cloud.uums.mapper")
public class UumApplication {

    public static void main(String[] args) {

        SpringApplication.run(UumApplication.class, args);
    }
}
