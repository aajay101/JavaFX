package com.hedgeflows.desktop.controller;

import com.hedgeflows.desktop.App;
import com.hedgeflows.desktop.service.AuthService;
import com.hedgeflows.desktop.service.AuthenticationException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    
    @FXML
    private Label errorLabel;

    private AuthService authService;

    @FXML
    private void initialize() {
        authService = new AuthService();
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String email = usernameField.getText();
        String password = passwordField.getText();
        
        try {
            // Use the authService to authenticate the user
            authService.login(email, password);
            // Navigate to the main layout
            App.setRoot("MainLayout");
        } catch (AuthenticationException e) {
            // Show error message for authentication failures
            errorLabel.setText("Invalid credentials. Please try again.");
            errorLabel.setVisible(true);
        } catch (Exception e) {
            // Print full stack trace and error message for other exceptions
            e.printStackTrace();
            System.out.println("CRITICAL ERROR: " + e.getMessage());
            errorLabel.setText("System error occurred. Please check logs.");
            errorLabel.setVisible(true);
        }
    }
    
    @FXML
    private void onRegisterClick() {
        try {
            App.setRoot("RegisterView");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CRITICAL ERROR: " + e.getMessage());
            errorLabel.setText("Error navigating to registration: " + e.getMessage());
            errorLabel.setVisible(true);
        }
    }
}