package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.dtos.request.FakeStoreProductDto;
import com.github.sbcharr.product_catalog.mappers.FakeStoreProductMapper;
import com.github.sbcharr.product_catalog.models.Product;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements IProductService {
    private static final String FAKE_STORE_PRODUCT_BY_ID_URL = "https://fakestoreapi.com/products/{productId}";
    private static final String FAKE_STORE_PRODUCT_URL = "https://fakestoreapi.com/products";
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response =
                restTemplate.getForEntity(FAKE_STORE_PRODUCT_URL, FakeStoreProductDto[].class);

        FakeStoreProductDto[] fakeStoreProductDtos = response.getBody();
        if (response.getStatusCode() != HttpStatus.OK || fakeStoreProductDtos == null || fakeStoreProductDtos.length == 0) {
            return List.of();
        }

        return Arrays.stream(fakeStoreProductDtos)
                .map(FakeStoreProductMapper.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public @Nullable Product getProductById(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                restTemplate.getForEntity(FAKE_STORE_PRODUCT_BY_ID_URL, FakeStoreProductDto.class, productId);

        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        if (fakeStoreProductDtoResponseEntity.getStatusCode() != HttpStatus.OK || fakeStoreProductDto == null) {
            return null;
        }

        return FakeStoreProductMapper.INSTANCE.toEntity(fakeStoreProductDto);
    }

    @Override
    public @Nullable Product createProduct(Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(FAKE_STORE_PRODUCT_URL,
                FakeStoreProductMapper.INSTANCE.toDto(product), FakeStoreProductDto.class);

        FakeStoreProductDto fakeStoreOutDto = response.getBody();
        if (response.getStatusCode() != HttpStatus.CREATED || fakeStoreOutDto == null) {
            return null;
        }

        return FakeStoreProductMapper.INSTANCE.toEntity(fakeStoreOutDto);
    }

    public <T> ResponseEntity<T> requestForEntity(String url, @org.springframework.lang.Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = this.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = this.responseEntityExtractor(responseType);
        return (ResponseEntity)nonNull((ResponseEntity)this.execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables));
    }
}
