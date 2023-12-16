package com.william.sprintbootmall.controller;

import com.william.sprintbootmall.constant.ProductCategory;
import com.william.sprintbootmall.constant.queryProductConditions;
import com.william.sprintbootmall.dto.ProductRequest;
import com.william.sprintbootmall.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.william.sprintbootmall.service.ProductService;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity <List<Product>> getProductlist(@RequestParam(required = false)ProductCategory category,
                                                         @RequestParam(required = false)String searchKey,
                                                         @RequestParam(required = false)String orderBy,
                                                         @RequestParam(required = false)String sort){
        queryProductConditions queryProductConditions = new queryProductConditions();
        queryProductConditions.setProductCategory(category);
        queryProductConditions.setSearchKey(searchKey);
        queryProductConditions.setOrderBy(orderBy);
        queryProductConditions.setSort(sort);

        List<Product> productList = productService.getProductlist(queryProductConditions);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if(product != null)
            return  ResponseEntity.status(HttpStatus.OK).body(product);
        else
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){//要讓@NotNull生效的話,要記得加上@Valid
        Integer productId = productService.createProduct(productRequest);

        if(productId > 0)
            return  ResponseEntity.status(HttpStatus.CREATED).body(productService.getProductById(productId));
        else
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductRequest productRequest,@PathVariable Integer productId){

        Product product =productService.getProductById(productId);
        if(product == null)
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

       productService.updateProduct(productRequest,productId);
       return  ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProductById(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
