package com.william.sprintbootmall.dao;

import com.william.sprintbootmall.dto.ProductRequest;
import com.william.sprintbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
