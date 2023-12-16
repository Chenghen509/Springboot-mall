package com.william.sprintbootmall.dao;

import com.william.sprintbootmall.constant.queryProductConditions;
import com.william.sprintbootmall.dto.ProductRequest;
import com.william.sprintbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProductlist(queryProductConditions queryProductConditions);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(ProductRequest productRequest,Integer productId);
    void  deleteProduct(Integer productId);
}
