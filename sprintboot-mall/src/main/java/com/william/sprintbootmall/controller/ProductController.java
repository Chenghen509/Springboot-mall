package com.william.sprintbootmall.controller;

import com.william.sprintbootmall.dto.ProductRequest;
import com.william.sprintbootmall.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.william.sprintbootmall.service.ProductService;
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if(product != null)
            return  ResponseEntity.status(HttpStatus.OK).body(product);
        else
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){//要讓@NotNull生效的話,要記得加上@Valid
        Integer productId = productService.createProduct(productRequest);

        if(productId > 0)
            return  ResponseEntity.status(HttpStatus.CREATED).body(productService.getProductById(productId));
        else
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}