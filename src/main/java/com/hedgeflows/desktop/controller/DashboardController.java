package com.hedgeflows.desktop.controller;

import java.text.NumberFormat;
import java.util.List;

import com.hedgeflows.desktop.model.Metric;
import com.hedgeflows.desktop.model.Trade;
import com.hedgeflows.desktop.service.DashboardService;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;

public class DashboardController {

    @FXML
    private Label totalBalanceLabel, cashInflowLabel, netHedgeLabel, activePositionsLabel;

    @FXML
    private BarChart<String, Number> cashflowChart;

    @FXML
    private TableView<Trade> tradesTable;

    @FXML
    private TableColumn<Trade, String> pairCol, typeCol, statusCol;
    
    @FXML
    private TableColumn<Trade, java.time.LocalDate> dateCol;

    @FXML
    private TableColumn<Trade, Double> amountCol;

    private DashboardService dashboardService;

    @FXML
    private void initialize() {
        dashboardService = new DashboardService();

        // Load metrics
        loadMetrics();

        // Load chart data
        loadChartData();

        // Load trades data
        loadTradesData();

        // Force charts + cards to respect layout spacing
        cashflowChart.setMinHeight(300);
        cashflowChart.setPrefHeight(350);
        cashflowChart.setMaxHeight(400);
        cashflowChart.setAnimated(false);

        // Smooth bar widths
        cashflowChart.setCategoryGap(20);
        cashflowChart.setBarGap(6);

        // Make the cards uniform height
        totalBalanceLabel.getParent().setStyle("-fx-min-height: 140px;");
        cashInflowLabel.getParent().setStyle("-fx-min-height: 140px;");
        netHedgeLabel.getParent().setStyle("-fx-min-height: 140px;");
        activePositionsLabel.getParent().setStyle("-fx-min-height: 140px;");
    }

    private void loadMetrics() {
        List<Metric> metrics = dashboardService.getKeyMetrics();

        if (metrics.size() >= 4) {
            totalBalanceLabel.setText(metrics.get(0).getValue());
            cashInflowLabel.setText(metrics.get(1).getValue());
            netHedgeLabel.setText(metrics.get(2).getValue());
            activePositionsLabel.setText(metrics.get(3).getValue());
        }
    }

    private void loadChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("Jan", 15000));
        series.getData().add(new XYChart.Data<>("Feb", 22000));
        series.getData().add(new XYChart.Data<>("Mar", 18000));
        series.getData().add(new XYChart.Data<>("Apr", 25000));
        series.getData().add(new XYChart.Data<>("May", 30000));
        series.getData().add(new XYChart.Data<>("Jun", 28000));

        cashflowChart.getData().clear();
        cashflowChart.getData().add(series);

        cashflowChart.setLegendVisible(false);
        cashflowChart.setVerticalGridLinesVisible(false);
        cashflowChart.setHorizontalGridLinesVisible(false);

        cashflowChart.setPadding(new Insets(10, 10, 10, 10));

        // Remove ugly default background
        cashflowChart.lookup(".chart-plot-background")
                     .setStyle("-fx-background-color: transparent;");
    }

    private void loadTradesData() {
        // Set up cell value factories
        pairCol.setCellValueFactory(new PropertyValueFactory<>("currencyPair"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Format amount column as currency
        amountCol.setCellFactory(tc -> new javafx.scene.control.TableCell<Trade, Double>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(NumberFormat.getCurrencyInstance().format(amount));
                }
            }
        });

        // Format status column with color
        statusCol.setCellFactory(tc -> new javafx.scene.control.TableCell<Trade, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                } else {
                    setText(status);
                    if ("OPEN".equals(status)) {
                        setStyle("-fx-text-fill: #10b981;"); // Emerald green
                    } else {
                        setStyle("-fx-text-fill: #64748b;"); // Slate gray
                    }
                }
            }
        });

        // Format date column
        dateCol.setCellFactory(column -> new javafx.scene.control.TableCell<Trade, java.time.LocalDate>() {
            @Override
            protected void updateItem(java.time.LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Use a formatter
                    setText(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy").format(item));
                }
            }
        });

        // Load data into table
        tradesTable.getItems().addAll(dashboardService.getRecentTrades());

        tradesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tradesTable.setFixedCellSize(42);
        tradesTable.setPadding(new Insets(10, 10, 10, 10));
        tradesTable.setStyle("-fx-background-radius: 12; -fx-padding: 8;");
    }
}

