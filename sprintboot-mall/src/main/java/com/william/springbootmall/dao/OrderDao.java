package com.william.springbootmall.dao;

import com.william.springbootmall.constant.buyItem;
import com.william.springbootmall.constant.createOrderRequest;
import com.william.springbootmall.model.Order;
import com.william.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
    Order getOrderById(Integer orderId);
    List<OrderItem> getOrderItemByOrderId(Integer orderId);
}
