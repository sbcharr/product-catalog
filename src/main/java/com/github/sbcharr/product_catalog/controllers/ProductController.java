package com.github.sbcharr.product_catalog.controllers;

import com.github.sbcharr.product_catalog.dtos.request.ProductRequestDto;
import com.github.sbcharr.product_catalog.dtos.response.ProductResponseDto;
import com.github.sbcharr.product_catalog.mappers.ProductMapper;
import com.github.sbcharr.product_catalog.models.Product;
import com.github.sbcharr.product_catalog.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    @Autowired
    @Qualifier("fakeStoreProductService")
    IProductService productService;

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productService.getAllProducts();

        return productList.stream()
                .map(ProductMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ProductMapper.INSTANCE.toDto(product);
    }

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        Product productIn = ProductMapper.INSTANCE.toEntity(requestDto);
        Product productOut = productService.createProduct(productIn);

        if (productOut == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return ProductMapper.INSTANCE.toDto(productOut);
    }
}
