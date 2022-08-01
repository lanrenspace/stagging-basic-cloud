package com.basic.cloud.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.basic.cloud.log.mapper")
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }
}
