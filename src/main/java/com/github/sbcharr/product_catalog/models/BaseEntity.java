package com.github.sbcharr.product_catalog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public abstract class BaseEntity {
    private long id;
    private Instant createdAt;
    private Instant updatedAt;
    @JsonIgnore
    private Status status; // to keep track of the deletion status (soft delete)
}
