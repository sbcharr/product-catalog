package com.github.sbcharr.product_catalog.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "product.fakestore")
@Setter
@Getter
public class FakeStoreApiConfig {
    private String baseUrl;
    private String timeoutConnectMs;
    private String timeoutReadMs;
    private int httpClientMaxConnectPool;
    private int httpClientMaxConnectPerRoute;
    private String httpIdleConnectionTimeoutMs;
}
