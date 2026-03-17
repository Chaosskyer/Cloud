package com.lzh.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient//启用服务注册与发现
@SpringBootApplication
public class ProductMainApplication {
    public static void main(String[] args){
        SpringApplication.run(ProductMainApplication.class,args);
    }
}
