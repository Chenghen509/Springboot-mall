package com.william.sprintbootmall.dao;

import com.william.sprintbootmall.constant.queryUserConditions;
import com.william.sprintbootmall.model.User;

public interface UserDao {
    Integer register(queryUserConditions queryUserConditions);

    User getUserId(Integer userId);
}
