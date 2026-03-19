package com.lzh.order.controller;

import com.lzh.order.service.OrderService;
import jakarta.annotation.Resource;
import lzh.Order.bean.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;
    @GetMapping("/order/{id}/{userId}")
    public Order createOrder(@PathVariable("id") Long id,
                             @PathVariable("userId") Long userId){
        return orderService.createOrder(id,userId);
    }
}
