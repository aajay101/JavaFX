package com.hedgeflows.desktop.service;

import com.hedgeflows.desktop.model.User;
import com.hedgeflows.desktop.repository.UserRepository;
import java.util.List;

public class UserService {
    
    private UserRepository userRepository;
    
    public UserService() {
        this.userRepository = new UserRepository();
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}