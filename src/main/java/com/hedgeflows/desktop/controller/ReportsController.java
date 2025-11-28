package com.hedgeflows.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private ListView<String> reportsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate the list with dummy report names
        reportsList.getItems().addAll(
                "End of Year Report 2024.pdf",
                "Q1 Cashflow Analysis.pdf",
                "March FX Statement.csv",
                "Portfolio Performance Report.xlsx",
                "Risk Assessment Document.pdf",
                "Compliance Audit Report.docx"
        );

        // Add double-click listener
        reportsList.setOnMouseClicked(this::handleReportDoubleClick);
    }

    private void handleReportDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selectedReport = reportsList.getSelectionModel().getSelectedItem();
            if (selectedReport != null) {
                // Show alert indicating download
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Download Report");
                alert.setHeaderText(null);
                alert.setContentText("Downloading " + selectedReport + "...");
                alert.showAndWait();
            }
        }
    }
}