package com.william.springbootmall.service.impelment;

import com.william.springbootmall.constant.buyItem;
import com.william.springbootmall.constant.createOrderRequest;
import com.william.springbootmall.constant.orderQueryParams;
import com.william.springbootmall.dao.OrderDao;
import com.william.springbootmall.dao.ProductDao;
import com.william.springbootmall.dao.UserDao;
import com.william.springbootmall.model.Order;
import com.william.springbootmall.model.Product;
import com.william.springbootmall.model.OrderItem;
import com.william.springbootmall.model.User;
import com.william.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Transactional
    @Override
    public Integer createOrder(Integer userId, createOrderRequest createOrderRequest) {

        //判斷userId是否存在
        User user = userDao.getUserById(userId);
        if(user == null) {
            log.warn("該user不存在!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(buyItem item : createOrderRequest.getBuyItemList()){
            Product product  = productDao.getProductById(item.getProductId());
            //判斷商品是否存在
            if(product == null){
                log.warn("商品Id:{} 不存在",item.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //判斷庫存數量是否足夠
            if(product.getStock() - item.getQuantity() < 0){
                log.warn("商品:{} 庫存不足，庫存數量:{},單據購買數量:{}",product.getProductName(),product.getStock(),item.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //扣庫存
            productDao.updateStock(product.getProductId(),product.getStock() - item.getQuantity());
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

    @Override
    public Order getOrderById(Integer orderId) {

        //查出單頭資料
        Order order = orderDao.getOrderById(orderId);
        //查出單身資料
        List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(orderId);
        //將單身寫進單頭
        order.setOrderItemList(orderItemList);

        return  order;
    }

    @Override
    public Integer countOrder(orderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrderList(orderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrderList(orderQueryParams);

        for(Order order : orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }
}
