package com.william.springbootmall.dao.implement;

import com.william.springbootmall.constant.queryUserConditions;
import com.william.springbootmall.dao.UserDao;
import com.william.springbootmall.model.User;
import com.william.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer register(queryUserConditions queryUserConditions) {
        String sql = "INSERT INTO users(email,password,created_date,last_modified_date)" +
                "VALUES(:email,:password,:createdDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("email",queryUserConditions.getEmail());
        map.put("password",queryUserConditions.getPassword());
        Date date = new Date();
        map.put("createdDate",date);
        map.put("lastModifiedDate",date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer userId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return  userId;

    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT * FROM users WHERE user_id = :userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);

        List<User>userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.isEmpty())
            return null;
        else
            return  userList.getFirst();
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);

        List<User>userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.isEmpty())
            return null;
        else
            return  userList.getFirst();
    }
}
