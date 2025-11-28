package com.hedgeflows.desktop.repository;

import com.google.gson.reflect.TypeToken;
import com.hedgeflows.desktop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository extends JsonRepository<User> {
    
    public UserRepository() {
        super("data/users.json", new TypeToken<ArrayList<User>>() {}.getType());
        // Removed debug print for admin seeding
    }

    @Override
    protected List<User> seedData() {
        // Return an empty list to start with no default users
        return new ArrayList<>();
    }
    
    public User findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}

