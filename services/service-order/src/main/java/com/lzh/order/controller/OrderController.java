package com.lzh.order.controller;

import com.lzh.order.service.OrderService;
import jakarta.annotation.Resource;
import lzh.Order.bean.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Resource
    OrderService orderService;
    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId){
        return orderService.createOrder(productId,userId);
    }
}
