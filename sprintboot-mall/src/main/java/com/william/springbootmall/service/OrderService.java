package com.william.springbootmall.service;

import com.william.springbootmall.constant.createOrderRequest;

public interface OrderService {
    Integer createOrder(Integer userId,createOrderRequest createOrderRequest);
}

