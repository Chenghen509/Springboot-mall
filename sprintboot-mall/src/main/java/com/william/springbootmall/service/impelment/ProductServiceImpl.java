package com.william.springbootmall.service.impelment;

import com.william.springbootmall.constant.queryProductConditions;
import com.william.springbootmall.dao.ProductDao;
import com.william.springbootmall.dto.ProductRequest;
import com.william.springbootmall.model.Product;
import com.william.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countProduct(queryProductConditions queryProductConditions) {
        return productDao.countProduct(queryProductConditions);
    }

    @Override
    public List<Product> getProductList(queryProductConditions queryProductConditions) {
        return  productDao.getProductList(queryProductConditions);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(ProductRequest productRequest, Integer productId) {
         productDao.updateProduct(productRequest,productId);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
