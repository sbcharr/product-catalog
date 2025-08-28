package com.github.sbcharr.product_catalog.controllers;

import com.github.sbcharr.product_catalog.dtos.ProductRequestDto;
import com.github.sbcharr.product_catalog.dtos.ProductResponseDto;
import com.github.sbcharr.product_catalog.dtos.search.SearchRequestDto;
import com.github.sbcharr.product_catalog.models.Product;
import com.github.sbcharr.product_catalog.services.IProductService;
import com.github.sbcharr.product_catalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    IProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        Product product = ProductController.toEntity(requestDto);
        product = productService.createProduct(product);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product");
        }
        ProductResponseDto productResponseDto = ProductResponseDto.fromEntity(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto requestDto, @PathVariable("id") Long productId) {
        if (productId == null || productId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Product ID");
        }
        Product product = ProductController.toEntity(requestDto);
        product = productService.updateProduct(product, productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Data or Product Not Found");
        }
        ProductResponseDto productResponseDto = ProductResponseDto.fromEntity(product);

        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productService.getAllProducts();

        return productList.stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        return ProductResponseDto.fromEntity(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
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

//    public static ProductResponseDto toDto(Product entity) {
//        ProductResponseDto dto = new ProductResponseDto();
//        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setDescription(entity.getDescription());
//        dto.setPrice(entity.getPrice());
//        dto.setImageurl(entity.getImageUrl());
//        dto.setCategory(entity.getCategory());
//
//        return dto;
//    }

    @PostMapping("/search")
    public Page<ProductResponseDto> searchProducts(@RequestBody SearchRequestDto searchRequestDto) {
        Page<Product> productPages = productService.searchProduct(
                searchRequestDto.getQuery(),
                searchRequestDto.getPageSize(),
                searchRequestDto.getPage(),
                searchRequestDto.getSortParams()
        );

        return productPages.map(ProductResponseDto::fromEntity);
    }
}
