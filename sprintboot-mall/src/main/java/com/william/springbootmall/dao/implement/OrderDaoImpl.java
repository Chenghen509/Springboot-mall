package com.william.springbootmall.dao.implement;


import com.william.springbootmall.constant.orderQueryParams;
import com.william.springbootmall.constant.queryProductConditions;
import com.william.springbootmall.dao.OrderDao;
import com.william.springbootmall.model.Order;
import com.william.springbootmall.model.OrderItem;
import com.william.springbootmall.model.Product;
import com.william.springbootmall.rowmapper.OrderItemRowMapper;
import com.william.springbootmall.rowmapper.OrderRowMapper;
import com.william.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO dcp_order(user_id,total_amount,created_date,last_modified_date)" +
                "VALUES(:userId,:totalAmount,:createdDate,:lastModifiedDate)";

        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);

        Date date = new Date();

        map.put("createdDate",date);
        map.put("lastModifiedDate",date);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int orderId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return  orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        //使用batchUpdate 一次性加入數據，比起用for loop 效率更好
        String sql = "INSERT INTO dcp_order_detail(order_id,product_id,quantity,amount)" +
                "VALUES(:orderId,:productId,:quantity,:amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
        for(int i = 0; i < orderItemList.size();i++){
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId",orderId);
            parameterSources[i].addValue("productId",orderItem.getProductId());
            parameterSources[i].addValue("quantity",orderItem.getQuantity());
            parameterSources[i].addValue("amount",orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT * FROM dcp_order WHERE order_id = :orderId";

        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);

        List<Order>orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());

        if(orderList.isEmpty())
            return null;
        else
            return orderList.getFirst();
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
        String sql = "SELECT A.order_item_id,A.order_id,A.product_id,A.quantity,A.amount,B.product_name,B.image_url FROM dcp_order_detail AS A " +
                "LEFT JOIN product AS B  ON A.product_id = B.product_id " +
                "WHERE A.order_id = :orderId";
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);

        List<OrderItem>orderItemList = namedParameterJdbcTemplate.query(sql,map,new OrderItemRowMapper());

        return  orderItemList;
    }

    @Override
    public Integer countOrder(orderQueryParams orderQueryParams) {
        String sql = "SELECT COUNT(*) FROM dcp_order WHERE 1=1";
        Map<String,Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,orderQueryParams);
        return namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
    }

    @Override
    public List<Order> getOrderList(orderQueryParams orderQueryParams) {
        String sql = "SELECT * FROM dcp_order where 1 = 1";

        Map<String,Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,orderQueryParams);

        //排序
        sql = sql + " ORDER BY created_date DESC ";
        //分頁
        sql = sql + " limit :limit "+ " " + "offset :offset";
        map.put("limit",orderQueryParams.getLimit());
        map.put("offset",orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());
        return orderList;
    }

    private String addFilteringSql(String sql, Map<String, Object> map, orderQueryParams orderQueryParams) {
        if(orderQueryParams.getUserId() != null){
            sql = sql + " AND user_id = :userId";
            map.put("userId",orderQueryParams.getUserId());
        }
        return sql;
    }

}
