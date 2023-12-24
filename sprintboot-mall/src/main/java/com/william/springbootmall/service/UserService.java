package com.william.springbootmall.service;

import com.william.springbootmall.constant.queryUserConditions;
import com.william.springbootmall.model.User;

public interface UserService {
    Integer register(queryUserConditions queryUserConditions);

    User getUserById(Integer userId);

    boolean login(queryUserConditions queryUserConditions);
}
