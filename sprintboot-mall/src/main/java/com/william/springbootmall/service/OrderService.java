package com.william.springbootmall.service;

import com.william.springbootmall.constant.createOrderRequest;
import com.william.springbootmall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId,createOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}

