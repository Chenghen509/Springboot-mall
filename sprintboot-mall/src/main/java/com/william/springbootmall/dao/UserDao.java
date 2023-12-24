package com.william.springbootmall.dao;

import com.william.springbootmall.constant.queryUserConditions;
import com.william.springbootmall.model.User;

public interface UserDao {
    Integer register(queryUserConditions queryUserConditions);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
