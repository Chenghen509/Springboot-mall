package com.william.springbootmall.dao.implement;

import com.william.springbootmall.constant.queryProductConditions;
import com.william.springbootmall.dao.ProductDao;
import com.william.springbootmall.dto.ProductRequest;
import com.william.springbootmall.model.Product;
import com.william.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Integer countProduct(queryProductConditions queryProductConditions) {
        String sql = "SELECT COUNT(*) FROM product WHERE 1=1";
        Map<String,Object> map = new HashMap<>();
        addFilteringSql(sql,map,queryProductConditions);
        return namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
    }

    @Override
    public List<Product> getProductlist(queryProductConditions queryProductConditions) {
        String sql = "SELECT * FROM product WHERE 1 = 1";
        Map<String,Object> map = new HashMap<>();
        addFilteringSql(sql,map,queryProductConditions);

        //排序
        sql = sql + " ORDER BY " + queryProductConditions.getOrderBy() + " " + queryProductConditions.getSort();
        //分頁
        sql = sql + " limit :limit "+ " " + "offset :offset";
        map.put("limit",queryProductConditions.getLimit());
        map.put("offset",queryProductConditions.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
        return productList;
    }

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

    @Override
    public void updateProduct(ProductRequest productRequest, Integer productId) {
       String sql = "UPDATE product SET product_name = :productName,category = :category," +
               "image_url = :imageUrl,price = :price,stock = :stock,description = :description" +
               ",last_modified_date = :lastModifiedDate WHERE product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        Date date = new Date();
        map.put("lastModifiedDate",date);

       namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);

    }
    private String addFilteringSql(String sql, Map<String, Object> map, queryProductConditions queryProductConditions) {
        if(queryProductConditions.getProductCategory() != null){
            sql = sql + " AND category = :category";
            map.put("category",queryProductConditions.getProductCategory().name());//enum類型需要額外轉換字串
        }
        if(queryProductConditions.getSearchKey() != null){
            sql = sql + " AND product_name LIKE :searchKey";
            map.put("searchKey","%" + queryProductConditions.getSearchKey() + "%");
        }
        return sql;
    }
}
