package com.lzh.order.service;


import lzh.Order.bean.Order;

public interface OrderService {
    Order createOrder(Long id, Long userId);
}
