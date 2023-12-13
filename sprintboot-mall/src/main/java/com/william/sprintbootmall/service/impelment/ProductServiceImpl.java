package com.william.sprintbootmall.service.impelment;

import com.william.sprintbootmall.dao.ProductDao;
import com.william.sprintbootmall.dto.ProductRequest;
import com.william.sprintbootmall.model.Product;
import com.william.sprintbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
