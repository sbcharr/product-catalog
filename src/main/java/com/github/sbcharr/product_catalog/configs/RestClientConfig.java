package com.github.sbcharr.product_catalog.configs;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class RestClientConfig {

    @Bean
    public RestClient restClient(FakeStoreApiConfig properties) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(properties.getHttpClientMaxConnectPool());
        connectionManager.setDefaultMaxPerRoute(properties.getHttpClientMaxConnectPerRoute());

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .evictIdleConnections(
                        TimeValue.ofMilliseconds(Long.parseLong(properties.getHttpIdleConnectionTimeoutMs())))
                .evictExpiredConnections()
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(Integer.parseInt(properties.getTimeoutConnectMs()));
        factory.setReadTimeout(Integer.parseInt(properties.getTimeoutReadMs()));

        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .requestFactory(factory::createRequest)
                .build();
    }
}

