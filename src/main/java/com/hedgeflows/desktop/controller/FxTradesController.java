package com.hedgeflows.desktop.controller;

import com.hedgeflows.desktop.model.Trade;
import com.hedgeflows.desktop.repository.TradeRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

public class FxTradesController implements Initializable {

    @FXML
    private TableView<Trade> tradesTable;

    @FXML
    private TableColumn<Trade, UUID> idColumn;

    @FXML
    private TableColumn<Trade, String> pairColumn;

    @FXML
    private TableColumn<Trade, String> typeColumn;

    @FXML
    private TableColumn<Trade, Double> amountColumn;

    @FXML
    private TableColumn<Trade, String> statusColumn;

    @FXML
    private TableColumn<Trade, LocalDate> dateColumn;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> statusFilter;

    @FXML
    private DatePicker dateFilter;

    @FXML
    private Button filterButton;

    @FXML
    private Button newTradeButton;

    private ObservableList<Trade> masterData = FXCollections.observableArrayList();
    private FilteredList<Trade> filteredData;

    private TradeRepository tradeRepository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tradeRepository = new TradeRepository();

        // Add icon to the new trade button
        FontIcon plusIcon = new FontIcon("fas-plus");
        plusIcon.setIconSize(16);
        newTradeButton.setGraphic(plusIcon);

        // Initialize the status filter combo box
        statusFilter.getItems().addAll("All", "OPEN", "CLOSED");
        statusFilter.setValue("All");

        // Set up the table columns
        setupTableColumns();

        // Load data
        loadData();

        // Set up search and filter
        setupSearchAndFilter();

        // Set up event handlers
        setupEventHandlers();
    }

    private void setupTableColumns() {
        // Set up cell value factories
        pairColumn.setCellValueFactory(new PropertyValueFactory<>("currencyPair"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Custom cell factories
        statusColumn.setCellFactory(new Callback<TableColumn<Trade, String>, TableCell<Trade, String>>() {
            @Override
            public TableCell<Trade, String> call(TableColumn<Trade, String> param) {
                return new TableCell<Trade, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setGraphic(null);
                            setStyle("");
                        } else {
                            setText(item);
                            if ("OPEN".equals(item)) {
                                setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-alignment: center;");
                            } else if ("CLOSED".equals(item)) {
                                setStyle("-fx-background-color: #94a3b8; -fx-text-fill: white; -fx-alignment: center;");
                            } else {
                                setStyle("");
                            }
                        }
                    }
                };
            }
        });

        amountColumn.setCellFactory(new Callback<TableColumn<Trade, Double>, TableCell<Trade, Double>>() {
            @Override
            public TableCell<Trade, Double> call(TableColumn<Trade, Double> param) {
                return new TableCell<Trade, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(String.format("$%,.2f", item));
                        }
                    }
                };
            }
        });

        dateColumn.setCellFactory(new Callback<TableColumn<Trade, LocalDate>, TableCell<Trade, LocalDate>>() {
            @Override
            public TableCell<Trade, LocalDate> call(TableColumn<Trade, LocalDate> param) {
                return new TableCell<Trade, LocalDate>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            try {
                                setText(item.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
                            } catch (Exception e) {
                                setText(item.toString());
                            }
                        }
                    }
                };
            }
        });
    }

    private void loadData() {
        masterData.clear();
        masterData.addAll(tradeRepository.findAll());
        
        filteredData = new FilteredList<>(masterData, p -> true);
        
        SortedList<Trade> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tradesTable.comparatorProperty());
        
        tradesTable.setItems(sortedData);
        
        // Apply styling to the table
        tradesTable.getStyleClass().add("card");
        try {
            tradesTable.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        } catch (Exception e) {
            // If CSS not found, continue without it
            e.printStackTrace();
        }
    }

    private void setupSearchAndFilter() {
        // Add a listener to the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        // Add a listener to the status filter
        statusFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        // Add a listener to the date filter
        dateFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        // Set up the filter button
        filterButton.setOnAction(event -> applyFilters());
    }

    private void applyFilters() {
        filteredData.setPredicate(trade -> {
            // Get the search term
            String lowerCaseFilter = searchField.getText().toLowerCase();

            // Check currency pair
            if (!lowerCaseFilter.isEmpty() && 
                !trade.getCurrencyPair().toLowerCase().contains(lowerCaseFilter)) {
                return false;
            }

            // Check status
            String statusValue = statusFilter.getValue();
            if (!"All".equals(statusValue) && !statusValue.equals(trade.getStatus())) {
                return false;
            }

            // Check date
            LocalDate selectedDate = dateFilter.getValue();
            if (selectedDate != null && !selectedDate.equals(trade.getDate())) {
                return false;
            }

            return true;
        });
    }

    private void setupEventHandlers() {
        // Set up the new trade button
        newTradeButton.setOnAction(event -> showNewTradeDialog());
    }

    private void showNewTradeDialog() {
        // Create the custom dialog
        Dialog<Trade> dialog = new Dialog<>();
        dialog.setTitle("New Trade");
        dialog.setHeaderText("Enter trade details");

        // Set the button types
        ButtonType okButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the form fields
        TextField pairField = new TextField();
        pairField.setPromptText("Currency Pair (e.g., EUR/USD)");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        ChoiceBox<String> typeChoice = new ChoiceBox<>();
        typeChoice.getItems().addAll("BUY", "SELL");
        typeChoice.setValue("BUY");

        ChoiceBox<String> statusChoice = new ChoiceBox<>();
        statusChoice.getItems().addAll("OPEN", "CLOSED");
        statusChoice.setValue("OPEN");

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        // Create the layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        grid.add(new Label("Currency Pair:"), 0, 0);
        grid.add(pairField, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Type:"), 0, 2);
        grid.add(typeChoice, 1, 2);
        grid.add(new Label("Status:"), 0, 3);
        grid.add(statusChoice, 1, 3);
        grid.add(new Label("Date:"), 0, 4);
        grid.add(datePicker, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a trade when the OK button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    String pair = pairField.getText();
                    Double amount = Double.parseDouble(amountField.getText());
                    String type = typeChoice.getValue();
                    String status = statusChoice.getValue();
                    LocalDate date = datePicker.getValue();

                    if (pair.isEmpty()) {
                        showAlert("Validation Error", "Currency pair cannot be empty");
                        return null;
                    }

                    return new Trade(pair, amount, type, status, date);
                } catch (NumberFormatException e) {
                    showAlert("Validation Error", "Please enter a valid amount");
                    return null;
                }
            }
            return null;
        });

        // Show the dialog and wait for user response
        dialog.showAndWait().ifPresent(trade -> {
            // Save the trade
            tradeRepository.save(trade);
            
            // Refresh the table
            loadData();
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}