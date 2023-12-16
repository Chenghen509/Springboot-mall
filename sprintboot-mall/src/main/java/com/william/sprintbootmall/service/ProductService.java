package com.william.sprintbootmall.service;

import com.william.sprintbootmall.dto.ProductRequest;
import com.william.sprintbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(ProductRequest productRequest,Integer productId);

    void deleteProduct(Integer productId);
}
