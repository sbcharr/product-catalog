package com.github.sbcharr.product_catalog.clients;

import com.github.sbcharr.product_catalog.dtos.request.FakeStoreProductDto;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreApiClient {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private static final String FAKE_STORE_PRODUCT_BY_ID_URL = "https://fakestoreapi.com/products/{productId}";
    private static final String FAKE_STORE_PRODUCT_URL = "https://fakestoreapi.com/products";
    RestClientException

    public @Nullable FakeStoreProductDto replaceFakeStoreProduct(FakeStoreProductDto fakeStoreProductDtoInput, Long id) {
        ResponseEntity<FakeStoreProductDto> response =
                requestForEntity(HttpMethod.PUT, FAKE_STORE_PRODUCT_BY_ID_URL, fakeStoreProductDtoInput,
                        FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDtoOutput = response.getBody();
        if (!validateResponse(response)) {
            return null;
        }

        return fakeStoreProductDtoOutput;
    }

    private boolean validateResponse(ResponseEntity<FakeStoreProductDto> fakeStoreProductResponseDto) {
        if (fakeStoreProductResponseDto.getBody() == null || fakeStoreProductResponseDto.getStatusCode() != HttpStatus.OK) {
            return false;
        }

        return true;
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
