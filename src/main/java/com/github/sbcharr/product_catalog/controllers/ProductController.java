package com.github.sbcharr.product_catalog.controllers;

import com.github.sbcharr.product_catalog.dtos.request.ProductRequestDto;
import com.github.sbcharr.product_catalog.dtos.response.ProductResponseDto;
import com.github.sbcharr.product_catalog.models.Product;
import com.github.sbcharr.product_catalog.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    @Autowired
    //@Qualifier("fakeStoreProductService")
    IProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        Product product = ProductController.toEntity(requestDto);
        product = productService.createProduct(product);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product");
        }

        return ProductController.toDto(product);
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(@RequestBody ProductRequestDto requestDto, @PathVariable("id") Long id) {
        Product product = ProductController.toEntity(requestDto);
        product = productService.updateProduct(product, id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Data or Product Not Found");
        }
        return ProductController.toDto(product);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productService.getAllProducts();

        return productList.stream()
                .map(ProductController::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        return ProductController.toDto(product);
    }

    @DeleteMapping("/products/{id}")
    public ProductResponseDto deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);

        return new ProductResponseDto(); // Return an empty DTO or handle as needed
    }

    public static Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageurl());
        product.setCategory(dto.getCategory());

        return product;
    }

    public static ProductResponseDto toDto(Product entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImageurl(entity.getImageUrl());
        dto.setCategory(entity.getCategory());

        return dto;
    }
}
