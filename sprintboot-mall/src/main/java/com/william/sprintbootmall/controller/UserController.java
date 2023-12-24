package com.william.sprintbootmall.controller;

import com.william.sprintbootmall.constant.queryUserConditions;
import com.william.sprintbootmall.model.User;
import com.william.sprintbootmall.service.ProductService;
import com.william.sprintbootmall.service.UserService;
import com.william.sprintbootmall.service.impelment.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("users/register")
    public ResponseEntity<User> register(@RequestBody @Valid queryUserConditions queryUserConditions) {
        Integer userId = userService.register(queryUserConditions);

        User user = userService.getUserId(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
