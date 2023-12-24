package com.william.springbootmall.service.impelment;

import com.william.springbootmall.constant.queryUserConditions;
import com.william.springbootmall.dao.UserDao;
import com.william.springbootmall.model.User;
import com.william.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);//寫法是制式化的,不用背

    @Autowired
    private   UserDao userDao;
    @Override
    public Integer register(queryUserConditions queryUserConditions) {
        //檢查註冊的email
        User user = userDao.getUserByEmail(queryUserConditions.getEmail());
        if(user != null) {
            log.warn("該信箱: {} 已經被註冊過了!",queryUserConditions.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = DigestUtils.md5DigestAsHex(queryUserConditions.getPassword().getBytes());
        queryUserConditions.setPassword(hashedPassword);

        //創建帳號
        return userDao.register(queryUserConditions);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public boolean login(queryUserConditions queryUserConditions) {
        User user = userDao.getUserByEmail(queryUserConditions.getEmail());
        String hashedPassword = DigestUtils.md5DigestAsHex(queryUserConditions.getPassword().getBytes());

        if(user == null){
            log.warn("該email: {} 尚未註冊!",queryUserConditions.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword().equals(hashedPassword)){
            return  true;
        }
        else{
            log.warn("email: {} 密碼不正確",queryUserConditions.getEmail());
            return false;
        }
    }
}
