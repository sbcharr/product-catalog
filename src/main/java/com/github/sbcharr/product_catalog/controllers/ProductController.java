package com.github.sbcharr.product_catalog.controllers;

import com.github.sbcharr.product_catalog.dtos.requestdtos.ProductRequestDto;
import com.github.sbcharr.product_catalog.dtos.responsedtos.ProductResponseDto;
import com.github.sbcharr.product_catalog.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    IProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDTO) {

    }

    @GetMapping("/products")
    public List<ProductRequestDto> getAllProducts() {
//        Product product1 = new Product();
//        product1.setId(1L);
//        product1.setPrice(1000D);
//
//        Product product2 = new Product();
//        product2.setId(2L);
//        product2.setPrice(2000D);
//
//        List<Product> productList = new ArrayList<>();
//        productList.add(product1);
//        productList.add(product2);
//
//        return productList;
        return null;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Long productId) {
        ProductRequestDto product = new ProductRequestDto();
        product.setId(2L);
        product.setPrice(2000D);

        return product;
    }
}
