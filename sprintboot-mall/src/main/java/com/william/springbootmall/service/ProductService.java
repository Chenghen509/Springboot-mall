package com.william.springbootmall.service;

import com.william.springbootmall.constant.queryProductConditions;
import com.william.springbootmall.dto.ProductRequest;
import com.william.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductlist(queryProductConditions queryProductConditions);

    Integer countProduct(queryProductConditions queryProductConditions);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(ProductRequest productRequest,Integer productId);

    void deleteProduct(Integer productId);
}
