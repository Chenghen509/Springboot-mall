package com.william.springbootmall.service;

import com.william.springbootmall.constant.createOrderRequest;
import com.william.springbootmall.constant.orderQueryParams;
import com.william.springbootmall.constant.queryProductConditions;
import com.william.springbootmall.model.Order;
import com.william.springbootmall.model.Product;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId,createOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
    Integer countOrder(orderQueryParams orderQueryParams);
    List<Order> getOrderList(orderQueryParams orderQueryParams);
}

