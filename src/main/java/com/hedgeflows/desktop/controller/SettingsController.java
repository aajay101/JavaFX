package com.hedgeflows.desktop.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hedgeflows.desktop.model.User;
import com.hedgeflows.desktop.service.AuthService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField companyField;

    @FXML
    private Button saveProfileButton;

    @FXML
    private CheckBox emailNotificationsCheckbox;

    @FXML
    private CheckBox darkModeCheckbox;

    @FXML
    private ComboBox<String> currencyComboBox;

    private AuthService authService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        authService = new AuthService();

        // Initialize the currency combo box
        currencyComboBox.getItems().addAll("USD", "GBP", "EUR");
        currencyComboBox.setValue("USD");

        // Pre-fill fields with mock user data (in a real app, this would come from the logged-in user)
        loadUserProfile();
    }

    private void loadUserProfile() {
        // Get the current user from AuthService
        User currentUser = authService.getCurrentUser();
        if (currentUser != null) {
            firstNameField.setText(currentUser.getFirstName());
            lastNameField.setText(currentUser.getLastName());
            emailField.setText(currentUser.getEmail());
            companyField.setText(currentUser.getCompanyName());
        } else {
            // Fallback to mock data if no user is logged in
            firstNameField.setText("Admin");
            lastNameField.setText("User");
            emailField.setText("admin@hedgeflows.com");
            companyField.setText("HedgeFlows");
        }
    }

    @FXML
    private void onSaveProfile() {
        // Get the current user from AuthService
        User currentUser = authService.getCurrentUser();
        if (currentUser != null) {
            // Update the user's information
            currentUser.setFirstName(firstNameField.getText());
            currentUser.setLastName(lastNameField.getText());
            currentUser.setEmail(emailField.getText());
            currentUser.setCompanyName(companyField.getText());
            
            // In a real application, you would save the updated user to the repository
            // For now, we'll just print a success message
            System.out.println("Profile updated for user: " + currentUser.getEmail());
        } else {
            System.out.println("No user logged in. Cannot save profile.");
        }

        // Show confirmation alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile Saved");
        alert.setHeaderText(null);
        alert.setContentText("Your profile has been saved successfully!");
        alert.showAndWait();
    }
}

