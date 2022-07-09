package com.basic.cloud.file;

import com.basic.cloud.file.utils.FastdfsUtil;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@MapperScan("com.basic.cloud.file.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public FastdfsUtil fastdfsUtil(FastFileStorageClient storageClient) {
        return new FastdfsUtil(storageClient);
    }
}
