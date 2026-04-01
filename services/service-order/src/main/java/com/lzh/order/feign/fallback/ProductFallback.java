package com.lzh.order.feign.fallback;

import com.lzh.order.feign.ProductFeignClient;
import lombok.extern.slf4j.Slf4j;
import lzh.Product.bean.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Slf4j
public class ProductFallback implements ProductFeignClient {
    @Override
    public Product getProductById(Long id) {
        log.info("服务兜底");
        Product product = new Product();
        product.setId(id);
        product.setNum(404);
        product.setProductName("未知商品");
        product.setPrice(new BigDecimal("999999"));
        return product;
    }
}
