package com.william.sprintbootmall.dao.implement;

import com.william.sprintbootmall.dao.ProductDao;
import com.william.sprintbootmall.dto.ProductRequest;
import com.william.sprintbootmall.model.Product;
import com.william.sprintbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.swing.plaf.basic.BasicTreeUI;
import java.util.*;

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

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name,category,image_url,price,stock," +
                "description,created_date,last_modified_date)" + "VALUES(:productName,:category,:imageUrl,:price," +
                ":stock,:description,:createDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date date = new Date();
        map.put("createDate",date);
        map.put("lastModifiedDate",date);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer productId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return  productId;

    }
}