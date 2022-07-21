package com.ywkj.cloud.basic.print.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ywkj.cloud.print.mapper")
public class PrintApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrintApplication.class, args);
    }
}
