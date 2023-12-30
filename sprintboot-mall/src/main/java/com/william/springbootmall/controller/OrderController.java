package com.william.springbootmall.controller;

import com.william.springbootmall.constant.*;
import com.william.springbootmall.model.Order;
import com.william.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid createOrderRequest createOrderRequest){
        Integer orderId = orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity <page<Order>> getOrders(@PathVariable Integer userId,
                                                         @RequestParam(defaultValue = "5")@Max(1000) @Min(0) Integer limit,
                                                         @RequestParam(defaultValue = "0")@Min(0) Integer offset){

        orderQueryParams orderQueryParams = new orderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        //取得OrderList
        List<Order> orderList = orderService.getOrderList(orderQueryParams);
        //取得訂單數量
        Integer total = orderService.countOrder(orderQueryParams);


        page page = new page();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(orderList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
