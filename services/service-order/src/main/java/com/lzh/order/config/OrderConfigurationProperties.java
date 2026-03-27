package com.lzh.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "order")
public class OrderConfigurationProperties {
    String timeout;
    String autoConfirm;
    String dbUrl;
}
