package com.lzh.product.controller;


import com.lzh.product.service.ProductService;
import jakarta.annotation.Resource;
import lzh.Product.bean.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Resource
    private ProductService productService;

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long id){
        System.out.println("接收到请求");
        return productService.getProduct(id);
    }
}
