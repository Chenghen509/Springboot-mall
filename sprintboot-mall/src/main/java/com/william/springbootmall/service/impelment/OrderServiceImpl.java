package com.william.springbootmall.service.impelment;

import com.william.springbootmall.constant.buyItem;
import com.william.springbootmall.constant.createOrderRequest;
import com.william.springbootmall.dao.OrderDao;
import com.william.springbootmall.dao.ProductDao;
import com.william.springbootmall.model.Product;
import com.william.springbootmall.model.OrderItem;
import com.william.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, createOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(buyItem item : createOrderRequest.getBuyItemList()){
            Product product  = productDao.getProductById(item.getProductId());
            //計算商品總金額
            int amount = product.getPrice() * item.getQuantity();
            totalAmount += amount;
            //將orderItem轉成buyItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setAmount(amount);
            orderItem.setQuantity(item.getQuantity());

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId,orderItemList);
        return orderId;

    }
}
