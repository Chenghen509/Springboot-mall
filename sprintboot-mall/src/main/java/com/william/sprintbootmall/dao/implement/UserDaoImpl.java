package com.william.sprintbootmall.dao.implement;

import com.william.sprintbootmall.constant.queryUserConditions;
import com.william.sprintbootmall.dao.UserDao;
import com.william.sprintbootmall.model.User;
import com.william.sprintbootmall.rowmapper.UserRowMapper;
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
        String sql = "INSERT INTO user(email,password,created_date,last_modified_date)" +
                "VALUES(:email,:password,:createdDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("email",queryUserConditions.getEmail());
        map.put("password",queryUserConditions.getPassword());
        Date date = new Date();
        map.put("createDate",date);
        map.put("lastModifiedDate",date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer userId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return  userId;

    }

    @Override
    public User getUserId(Integer userId) {
        String sql = "SELECT * FROM user WHERE user_id = :userId";
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);

        List<User>userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.isEmpty())
            return null;
        else
            return  userList.getFirst();
    }
}
