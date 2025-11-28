package com.hedgeflows.desktop.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hedgeflows.desktop.model.BaseEntity;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class JsonRepository<T extends BaseEntity> implements Repository<T> {
    protected String filePath;
    protected Gson gson;
    protected java.lang.reflect.Type listType;

    public JsonRepository(String filePath, java.lang.reflect.Type listType) {
        this.filePath = filePath;
        this.listType = listType;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        initializeFile();
    }

    private void initializeFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                List<T> seedData = seedData();
                saveAll(seedData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.length() == 0) {
            List<T> seedData = seedData();
            saveAll(seedData);
        }
    }

    protected abstract List<T> seedData();

    @Override
    public List<T> findAll() {
        try (Reader reader = new FileReader(filePath)) {
            List<T> entities = gson.fromJson(reader, listType);
            return entities != null ? entities : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<T> findById(UUID id) {
        List<T> entities = findAll();
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    @Override
    public T save(T entity) {
        System.out.println("DEBUG: Saving entity to JSON: " + entity.toString());
        
        List<T> entities = findAll();
        
        // Check if entity already exists
        boolean exists = false;
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(entity.getId())) {
                entities.set(i, entity);
                exists = true;
                break;
            }
        }
        
        // If not exists, add new entity
        if (!exists) {
            entities.add(entity);
        }
        
        saveAll(entities);
        return entity;
    }

    private void saveAll(List<T> entities) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(entities, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        List<T> entities = findAll();
        entities.removeIf(entity -> entity.getId().equals(id));
        saveAll(entities);
    }
}