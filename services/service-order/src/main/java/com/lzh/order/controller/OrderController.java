package com.lzh.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lzh.order.config.OrderConfigurationProperties;
import com.lzh.order.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lzh.Order.bean.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
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
    // 创建订单
    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId){
        Order order = orderService.createOrder(productId,userId);
        return order;
    }
    @SentinelResource(value = "skill",fallback = "skillFallback")
    @GetMapping("/skill")
    public Order skill(@RequestParam("userId") Long userId,
                       @RequestParam("productId") Long productId){
        Order order = orderService.createOrder(productId,userId);
        order.setId(Long.MAX_VALUE);
        return order;
    }
    public Order skillFallback(Long userId, Long productId, Throwable e){
        Order order = new Order();
        order.setId(userId);
        order.setUserId(productId);
        order.setNickName("查询商品失败了："+e.getClass());
        return order;
    }
    @GetMapping("/write")
    public String write(){
        return "write";
    }
    @GetMapping("/read")
    public String read(){
        log.info("read success");
        return "read";
    }
}
