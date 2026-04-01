package com.lzh.order;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 订单服务主启动类
 * 配置并启动 Spring Boot 应用，同时注册 Nacos 配置监听器
 * 用于动态监听和响应 Nacos 配置中心的配置文件变化
 */
@Slf4j
@SpringBootApplication
@EnableFeignClients
public class OrderMainApplication {
    /**
     * 应用程序主入口方法
     * 启动 Spring Boot 应用
     *
     * @param args 命令行参数数组
     */
    public static void main(String[] args){
        SpringApplication.run(OrderMainApplication.class,args);
    }

    /**
     * 创建并返回 ApplicationRunner 实例，用于应用启动后执行初始化逻辑
     * 主要功能：注册 Nacos 配置监听器，监听 service-order.yml 配置文件的变更
     *
     * @param nacosConfigManager Nacos 配置管理器，用于获取 Nacos 配置服务实例
     * @return ApplicationRunner 应用运行器，在应用启动完成后执行
     */
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager){
        return args -> {
            log.info("==启动 Nacos 监听器==");
            ConfigService configService = nacosConfigManager.getConfigService();

            /**
             * 注册 Nacos 配置监听器
             * 监听 service-order.yml 配置文件在 DEFAULT_GROUP 组中的变更
             * 当配置文件发生变化时，使用固定大小为 4 的线程池异步处理配置更新
             */
            configService.addListener("service-order.yml", "DEFAULT_GROUP", new Listener(){
                @Override
                public Executor getExecutor() {
                    return Executors.newFixedThreadPool(4);
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("==Nacos 监听到配置文件更新==");
                    log.warn("配置文件内容{}",configInfo);
                }
            });
            log.info("==Nacos 监听器启动完毕==");
        };
    }
}


