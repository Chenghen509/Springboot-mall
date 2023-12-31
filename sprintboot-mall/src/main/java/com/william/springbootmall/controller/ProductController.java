package com.william.springbootmall.controller;

import com.william.springbootmall.constant.ProductCategory;
import com.william.springbootmall.constant.page;
import com.william.springbootmall.constant.queryProductConditions;
import com.william.springbootmall.dto.ProductRequest;
import com.william.springbootmall.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.william.springbootmall.service.ProductService;

import java.util.List;
@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity <page<Product>> getProducts(@RequestParam(required = false)ProductCategory category,
                                                         @RequestParam(required = false)String searchKey,
                                                         @RequestParam(defaultValue = "created_date")String orderBy,
                                                         @RequestParam(defaultValue = "asc")String sort,
                                                         @RequestParam(defaultValue = "5")@Max(1000) @Min(0) Integer limit,
                                                         @RequestParam(defaultValue = "0")@Min(0) Integer offset){
        queryProductConditions queryProductConditions = new queryProductConditions();
        queryProductConditions.setProductCategory(category);
        queryProductConditions.setSearchKey(searchKey);
        queryProductConditions.setOrderBy(orderBy);
        queryProductConditions.setSort(sort);
        queryProductConditions.setLimit(limit);
        queryProductConditions.setOffset(offset);

        List<Product> productList = productService.getProductList(queryProductConditions);
        Integer total = productService.countProduct(queryProductConditions);

        page page = new page();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
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
