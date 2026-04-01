package com.lzh.product.service;

import lzh.Product.bean.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService{


    @Override
    public Product getProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setNum(1);
        product.setProductName("华人牌2060款手机");
        product.setPrice(new BigDecimal("520"));
        return product;
    }
}
