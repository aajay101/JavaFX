package com.hedgeflows.desktop.controller;

import java.io.IOException;

import com.hedgeflows.desktop.App;
import com.hedgeflows.desktop.model.User;
import com.hedgeflows.desktop.service.AuthService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label userNameLabel;

    private AuthService authService;

    @FXML
    private void initialize() {
        // Initialize auth service
        authService = new AuthService();

        // Set the user's name in the sidebar from the logged-in user
        User currentUser = authService.getCurrentUser();
        if (currentUser != null) {
            userNameLabel.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        } else {
            userNameLabel.setText("Guest");
        }

        // Load the dashboard by default
        loadDashboard();
    }

    @FXML
    private void onDashboardClick() {
        loadDashboard();
    }

    @FXML
    private void onPaymentsClick() {
        loadView("PaymentsView");
    }

    @FXML
    private void onFxTradesClick() {
        loadView("FxTradesView");
    }

    @FXML
    private void onReportsClick() {
        loadView("ReportsView");
    }

    @FXML
    private void onSettingsClick() {
        loadView("SettingsView");
    }

    @FXML
    private void onLogoutClick() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDashboard() {
        // Dashboard is the default screen
        loadView("DashboardView");
    }

    // This method MUST remain private. App.java should never call it.
    private void loadView(String fxmlPath) {
        try {
            contentArea.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/hedgeflows/desktop/view/" + fxmlPath + ".fxml"));

            Node view = loader.load();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load view: " + fxmlPath, e);
        }
    }
}

