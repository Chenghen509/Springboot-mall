package com.william.sprintbootmall.service;

import com.william.sprintbootmall.constant.queryProductConditions;
import com.william.sprintbootmall.dto.ProductRequest;
import com.william.sprintbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductlist(queryProductConditions queryProductConditions);

    Integer countProduct(queryProductConditions queryProductConditions);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(ProductRequest productRequest,Integer productId);

    void deleteProduct(Integer productId);
}
