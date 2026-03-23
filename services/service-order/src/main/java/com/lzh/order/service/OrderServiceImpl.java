package com.lzh.order.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lzh.Order.bean.Order;
import lzh.Product.bean.Product;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    RestTemplate restTemplate;

    @Override
    public Order createOrder(Long productId, Long userId) {
        Product product = getProduct(productId);
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("陆小千");
        order.setAddress("上海");
        order.setProductList(Arrays.asList(product));
        return order;
    }

    private Product getProduct(Long productId){
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance instance = instances.get(0);
        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        log.info("远程请求:{}",url);
        return restTemplate.getForObject(url,Product.class);
    }
}
