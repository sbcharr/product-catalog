package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.clients.FakeStoreApiClient;
import com.github.sbcharr.product_catalog.dtos.FakeStoreProductDto;
import com.github.sbcharr.product_catalog.models.Category;
import com.github.sbcharr.product_catalog.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("fakeStoreProductService")
@Slf4j
public class FakeStoreProductService implements IProductService {
    private final FakeStoreApiClient apiClient;
    private final RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(FakeStoreApiClient apiClient, RedisTemplate<String, Object> redisTemplate) {
        this.apiClient = apiClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product.getName());
        FakeStoreProductDto inDto = toFakeStoreDto(product);
        FakeStoreProductDto outDto = apiClient.createFakeStoreProduct(inDto);

        return toEntityFromFakeStoreDto(outDto);
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        log.info("Updating product id={}", productId);
        FakeStoreProductDto inDto = toFakeStoreDto(product);
        FakeStoreProductDto outDto = apiClient.replaceFakeStoreProduct(inDto, productId);

        return toEntityFromFakeStoreDto(outDto);
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        FakeStoreProductDto[] fakeStoreProductDtos = apiClient.getAllProducts();

        return Arrays.stream(fakeStoreProductDtos)
                .map(this::toEntityFromFakeStoreDto)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Long productId) {
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + productId);
        if (product == null) {
            // cache HIT
            return product;
        }
        // cache MISS
        log.info("cache miss for product id={}", productId);
        FakeStoreProductDto fakeStoreProductDto = apiClient.getProductById(productId);
        if (fakeStoreProductDto != null) {
            product = toEntityFromFakeStoreDto(fakeStoreProductDto);
            // save in cache
            redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, product);
        }

        return product;
    }

    @Override
    public void deleteProductById(Long productId) {
        apiClient.deleteProductById(productId);
    }

    public Product toEntityFromFakeStoreDto(FakeStoreProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImage());
        if (dto.getCategory() != null) {
            Category category = new Category();
            category.setName(dto.getCategory());
            product.setCategory(category);
        }

        return product;
    }

    public FakeStoreProductDto toFakeStoreDto(Product entity) {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImage(entity.getImageUrl());
        dto.setCategory(entity.getCategory().getName());

        return dto;
    }
}
