package com.hedgeflows.desktop.repository;

import com.hedgeflows.desktop.model.BaseEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T extends BaseEntity> {
    List<T> findAll();
    Optional<T> findById(UUID id);
    T save(T entity);
    void delete(UUID id);
}