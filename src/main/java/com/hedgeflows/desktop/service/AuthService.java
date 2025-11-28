package com.hedgeflows.desktop.service;

import com.hedgeflows.desktop.model.User;
import com.hedgeflows.desktop.repository.UserRepository;

public class AuthService {
    private UserRepository userRepository;
    private User currentUser;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public User login(String email, String password) throws AuthenticationException {
        System.out.println("DEBUG: Login attempt for: " + email);
        System.out.println("DEBUG: Searching in repository...");
        
        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            System.out.println("DEBUG: User NOT found in database.");
            throw new AuthenticationException("User not found");
        }
        
        System.out.println("DEBUG: User found: " + user.getEmail());
        System.out.println("DEBUG: Input Password: '" + password + "'");
        System.out.println("DEBUG: Stored Password: '" + user.getPassword() + "'");
        System.out.println("DEBUG: Match result: " + (password.equals(user.getPassword())));
        
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid password");
        }
        
        this.currentUser = user;
        return user;
    }

    public User register(User user) {
        // The user's UUID and createdAt are set in the User constructor
        // Save the user to the repository
        User savedUser = userRepository.save(user);
        this.currentUser = savedUser;
        return savedUser;
    }
    
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    public void logout() {
        this.currentUser = null;
    }
}

