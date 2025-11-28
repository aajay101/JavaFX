package com.hedgeflows.desktop.controller;

import com.hedgeflows.desktop.App;
import com.hedgeflows.desktop.model.User;
import com.hedgeflows.desktop.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class RegisterController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField companyField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label errorLabel;

    private AuthService authService;

    @FXML
    private void initialize() {
        authService = new AuthService();
        registerButton.setOnAction(event -> onRegister());
    }

    @FXML
    private void onRegister() {
        System.out.println("DEBUG: Register button clicked for: " + emailField.getText());
        
        // Validate that fields are not empty
        if (firstNameField.getText().trim().isEmpty() ||
            lastNameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            companyField.getText().trim().isEmpty() ||
            passwordField.getText().trim().isEmpty()) {
            
            errorLabel.setText("All fields are required.");
            errorLabel.setVisible(true);
            return;
        }

        try {
            // Create a new User object
            User user = new User(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                emailField.getText().trim(),
                passwordField.getText().trim(),
                companyField.getText().trim()
            );

            // Call authService.register(user)
            authService.register(user);

            // Show success alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Account Created");
            alert.setHeaderText(null);
            alert.setContentText("Your account has been created successfully. Please log in now.");
            alert.showAndWait();

            // Switch scene to LoginView.fxml
            App.setRoot("login");
        } catch (Exception e) {
            // Show error message in errorLabel
            errorLabel.setText("Registration failed: " + e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void onBackToLogin() {
        // Switch scene to LoginView.fxml
        try {
            App.setRoot("login");
        } catch (Exception e) {
            errorLabel.setText("Error navigating to login: " + e.getMessage());
            errorLabel.setVisible(true);
        }
    }
}