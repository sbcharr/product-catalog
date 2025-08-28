package com.github.sbcharr.product_catalog.clients;

import com.github.sbcharr.product_catalog.dtos.FakeStoreProductDto;
import com.github.sbcharr.product_catalog.exceptions.FakeStoreApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class FakeStoreApiClient {
    private static final String PRODUCT_BY_ID_PATH = "/products/{id}";
    private static final String PRODUCTS_PATH = "/products";

    private final RestClient restClient;

    public FakeStoreApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Creates a product.
     */
    public FakeStoreProductDto createFakeStoreProduct(FakeStoreProductDto fakeStoreProductDtoInput) {
        ResponseEntity<FakeStoreProductDto> response = restClient.post()
                .uri(PRODUCTS_PATH)
                .body(fakeStoreProductDtoInput)
                .retrieve()
                .toEntity(FakeStoreProductDto.class);

        return extractOrThrow(response, "create product");
    }

    /**
     * Updates/Replaces a product.
     */
    public FakeStoreProductDto replaceFakeStoreProduct(FakeStoreProductDto fakeStoreProductDtoInput, Long id) {
        log.debug("Replacing product with id={} via FakeStore API", id);
        ResponseEntity<FakeStoreProductDto> response = restClient.put()
                .uri(PRODUCT_BY_ID_PATH, id)
                .body(fakeStoreProductDtoInput)
                .retrieve()
                .toEntity(FakeStoreProductDto.class);

        return extractOrThrow(response, "replace product id=" + id);
    }

    /**
     * Retrieves all products.
     */
    public FakeStoreProductDto[] getAllProducts() {
        log.debug("Fetching all products from FakeStore API");
        ResponseEntity<FakeStoreProductDto[]> response = restClient.get()
                .uri(PRODUCTS_PATH)
                .retrieve()
                .toEntity(FakeStoreProductDto[].class);

        return extractOrThrow(response, "fetch all products");
    }

    /**
     * Retrieves a product by id.
     */
    public FakeStoreProductDto getProductById(Long id) {
        log.debug("Fetching product with id={} via FakeStore API", id);
        ResponseEntity<FakeStoreProductDto> response = restClient.get()
                .uri(PRODUCT_BY_ID_PATH, id)
                .retrieve()
                .toEntity(FakeStoreProductDto.class);

        return extractOrThrow(response, "fetch product id=" + id);
    }

    /**
     * Deletes a product by id.
     */
    public FakeStoreProductDto deleteProductById(Long id) {
        log.debug("Deleting product with id={} via FakeStore API", id);
        ResponseEntity<FakeStoreProductDto> response = restClient.delete()
                .uri(PRODUCT_BY_ID_PATH, id)
                .retrieve()
                .toEntity(FakeStoreProductDto.class);

        return extractOrThrow(response, "delete product id=" + id);
    }

    private <T> T extractOrThrow(ResponseEntity<T> response, String action) {
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new FakeStoreApiException("Failed to " + action + ", status=" + response.getStatusCode());
        }
        return response.getBody();
    }
}
