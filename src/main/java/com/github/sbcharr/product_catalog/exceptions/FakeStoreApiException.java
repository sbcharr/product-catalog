package com.github.sbcharr.product_catalog.exceptions;

public class FakeStoreApiException extends RuntimeException {
    public FakeStoreApiException(String message) {
        super(message);
    }

    public FakeStoreApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
