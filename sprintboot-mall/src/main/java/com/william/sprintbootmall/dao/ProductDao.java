package com.william.sprintbootmall.dao;

import com.william.sprintbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
