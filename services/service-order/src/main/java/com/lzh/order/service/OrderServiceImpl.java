package com.lzh.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.lzh.order.config.OrderConfigurationProperties;
import com.lzh.order.feign.ProductFeignClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lzh.Order.bean.Order;
import lzh.Product.bean.Product;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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

    @Resource
    LoadBalancerClient loadBalancerClient;

    @Resource
    ProductFeignClient productFeignClient;
    @SentinelResource(value = "创建订单服务",blockHandler = "createOrderFallback")
    @Override
    public Order createOrder(Long productId, Long userId) {
//        Product product = getProductwithBalancer(productId);
        Product product = productFeignClient.getProductById(productId);
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("陆小千");
        order.setAddress("上海");
        order.setProductList(Arrays.asList(product));
        return order;
    }

    public Order createOrderFallback(Long productId, Long userId, BlockException e) {
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(new BigDecimal(999999));
        order.setUserId(userId);
        order.setNickName("未知用户");
        order.setAddress("异常报错信息："+e.getClass());
        return order;
    }

    /**
     * 不带负载均衡的方式
     * @param productId
     * @return
     */
    private Product getProduct(Long productId){
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance instance = instances.get(0);
        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        log.info("远程请求:{}",url);
        return restTemplate.getForObject(url,Product.class);
    }
    /**
     * 带负载均衡的方式
     * @param productId
     * @return
     */
    private Product getProductwithBalancerClient(Long productId){
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        String url = "http://"+choose.getHost()+":"+choose.getPort()+"/product/"+productId;
        log.info("远程请求:{}",url);
        return restTemplate.getForObject(url,Product.class);
    }
    /**
     * 带注解型负载均衡的方式
     * @param productId
     * @return
     */
    private Product getProductwithBalancer(Long productId){
        String url = "http://service-product/product/"+productId;
        log.info("远程请求:{}",url);
        return restTemplate.getForObject(url,Product.class);
    }
}
