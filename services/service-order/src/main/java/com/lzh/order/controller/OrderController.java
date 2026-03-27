package com.lzh.order.controller;

import com.lzh.order.config.OrderConfigurationProperties;
import com.lzh.order.service.OrderService;
import jakarta.annotation.Resource;
import lzh.Order.bean.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Resource
    OrderService orderService;

    @Resource
    OrderConfigurationProperties orderConfigurationProperties;
    @GetMapping("/config")
    public String config(){
        return "order.timeout="+orderConfigurationProperties.getTimeout()+";\n"
                +"order.auto-confirm="+orderConfigurationProperties.getAutoConfirm()+";\n"
                +"db-url="+ orderConfigurationProperties.getDbUrl();
    }
    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId){
        return orderService.createOrder(productId,userId);
    }
}
