package com.william.sprintbootmall.service.impelment;

import com.william.sprintbootmall.constant.queryUserConditions;
import com.william.sprintbootmall.dao.UserDao;
import com.william.sprintbootmall.model.User;
import com.william.sprintbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private   UserDao userDao;
    @Override
    public Integer register(queryUserConditions queryUserConditions) {
        return userDao.register(queryUserConditions);
    }

    @Override
    public User getUserId(Integer userId) {
        return userDao.getUserId(userId);
    }
}
