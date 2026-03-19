package com.lzh.order.service;

import lzh.Order.bean.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public Order createOrder(Long id, Long userId) {
        Order order = new Order();
        order.setId(id);
        order.setTotalAmount(new BigDecimal("520"));
        order.setUserId(userId);
        order.setNickName("陆小千");
        order.setAddress("上海");
        order.setProductList(null);
        return order;
    }
}
