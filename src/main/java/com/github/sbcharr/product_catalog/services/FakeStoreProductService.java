package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.clients.FakeStoreApiClient;
import com.github.sbcharr.product_catalog.dtos.request.FakeStoreProductDto;
import com.github.sbcharr.product_catalog.dtos.request.ProductRequestDto;
import com.github.sbcharr.product_catalog.models.Category;
import com.github.sbcharr.product_catalog.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("fakeStoreProductService")
@RequiredArgsConstructor
@Slf4j
public class FakeStoreProductService implements IProductService {
    private final FakeStoreApiClient apiClient;

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
        FakeStoreProductDto fakeStoreProductDto = apiClient.getProductById(productId);
        return toEntityFromFakeStoreDto(fakeStoreProductDto);
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

    public Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageurl());
        product.setCategory(dto.getCategory());

        return product;
    }

    public ProductRequestDto toDto(Product entity) {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImageurl(entity.getImageUrl());
        dto.setCategory(entity.getCategory());

        return dto;
    }
}
