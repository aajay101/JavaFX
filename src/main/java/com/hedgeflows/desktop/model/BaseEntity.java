package com.hedgeflows.desktop.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class BaseEntity {
    protected UUID id;
    protected LocalDateTime createdAt;

    public BaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public BaseEntity(UUID id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}

