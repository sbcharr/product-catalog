package com.github.sbcharr.product_catalog.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public abstract class BaseEntity {
    private long id;
    private Instant createdAt;
    private Instant updatedAt;
    private Status status;
}
