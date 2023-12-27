package com.william.springbootmall.dao;

import com.william.springbootmall.constant.queryProductConditions;
import com.william.springbootmall.dto.ProductRequest;
import com.william.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    Integer countProduct(queryProductConditions queryProductConditions);
    List<Product> getProductlist(queryProductConditions queryProductConditions);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(ProductRequest productRequest,Integer productId);
    void deleteProduct(Integer productId);
    void updateStock(Integer productId,Integer stock);
}
