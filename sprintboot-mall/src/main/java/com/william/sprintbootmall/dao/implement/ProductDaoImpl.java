package com.william.sprintbootmall.dao.implement;

import com.william.sprintbootmall.dao.ProductDao;
import com.william.sprintbootmall.model.Product;
import com.william.sprintbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Product getProductById(Integer productId) {

            String sql = "SELECT * FROM product WHERE product_id = :productId";
            Map<String,Object> map = new HashMap<>();
            map.put("productId",productId);

            List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

            if(productList.isEmpty())
                return null;
            else
                return  productList.getFirst();
        }
}
