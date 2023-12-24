package com.william.springbootmall.controller;

import com.william.springbootmall.constant.queryUserConditions;
import com.william.springbootmall.model.User;
import com.william.springbootmall.service.impelment.UserServiceImpl;
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

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("users/login")
    public ResponseEntity<User> login(@RequestBody @Valid queryUserConditions queryUserConditions){
        boolean isLoginSuccess = userService.login(queryUserConditions);
        if(isLoginSuccess)
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
