package com.william.sprintbootmall.service;

import com.william.sprintbootmall.constant.queryUserConditions;
import com.william.sprintbootmall.model.User;

public interface UserService {
    Integer register(queryUserConditions queryUserConditions);

    User getUserId(Integer userId);
}
