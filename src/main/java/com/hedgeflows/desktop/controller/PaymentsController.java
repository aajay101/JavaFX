package com.hedgeflows.desktop.controller;

import com.hedgeflows.desktop.model.Payment;
import com.hedgeflows.desktop.service.PaymentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PaymentsController implements Initializable {

    @FXML
    private TableView<Payment> outstandingTable;

    @FXML
    private TableColumn<Payment, String> outstandingBeneficiaryColumn;

    @FXML
    private TableColumn<Payment, Double> outstandingAmountColumn;

    @FXML
    private TableColumn<Payment, LocalDate> outstandingDueDateColumn;

    @FXML
    private TableColumn<Payment, String> outstandingStatusColumn;

    @FXML
    private TableView<Payment> paidTable;

    @FXML
    private TableColumn<Payment, String> paidBeneficiaryColumn;

    @FXML
    private TableColumn<Payment, Double> paidAmountColumn;

    @FXML
    private TableColumn<Payment, LocalDate> paidDueDateColumn;

    @FXML
    private TableColumn<Payment, String> paidStatusColumn;

    private ObservableList<Payment> outstandingPayments = FXCollections.observableArrayList();
    private ObservableList<Payment> paidPayments = FXCollections.observableArrayList();

    private PaymentService paymentService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paymentService = new PaymentService();

        // Set up the table columns
        setupTableColumns();

        // Load data
        loadData();
    }

    private void setupTableColumns() {
        // Set up outstanding table columns
        outstandingBeneficiaryColumn.setCellValueFactory(new PropertyValueFactory<>("beneficiary"));
        outstandingAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        outstandingDueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        outstandingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set up paid table columns
        paidBeneficiaryColumn.setCellValueFactory(new PropertyValueFactory<>("beneficiary"));
        paidAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        paidDueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        paidStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Custom cell factories for amount formatting
        outstandingAmountColumn.setCellFactory(getAmountCellFactory());
        paidAmountColumn.setCellFactory(getAmountCellFactory());

        // Custom cell factories for date formatting
        outstandingDueDateColumn.setCellFactory(getDateCellFactory());
        paidDueDateColumn.setCellFactory(getDateCellFactory());

        // Custom cell factories for status styling
        outstandingStatusColumn.setCellFactory(getStatusCellFactory());
        paidStatusColumn.setCellFactory(getStatusCellFactory());
    }

    private void loadData() {
        outstandingPayments.clear();
        paidPayments.clear();

        outstandingPayments.addAll(paymentService.getOutstandingPayments());
        paidPayments.addAll(paymentService.getPaidPayments());

        outstandingTable.setItems(outstandingPayments);
        paidTable.setItems(paidPayments);
    }

    private Callback<TableColumn<Payment, Double>, TableCell<Payment, Double>> getAmountCellFactory() {
        return new Callback<TableColumn<Payment, Double>, TableCell<Payment, Double>>() {
            @Override
            public TableCell<Payment, Double> call(TableColumn<Payment, Double> param) {
                return new TableCell<Payment, Double>() {
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
        };
    }

    private Callback<TableColumn<Payment, LocalDate>, TableCell<Payment, LocalDate>> getDateCellFactory() {
        return new Callback<TableColumn<Payment, LocalDate>, TableCell<Payment, LocalDate>>() {
            @Override
            public TableCell<Payment, LocalDate> call(TableColumn<Payment, LocalDate> param) {
                return new TableCell<Payment, LocalDate>() {
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
        };
    }

    private Callback<TableColumn<Payment, String>, TableCell<Payment, String>> getStatusCellFactory() {
        return new Callback<TableColumn<Payment, String>, TableCell<Payment, String>>() {
            @Override
            public TableCell<Payment, String> call(TableColumn<Payment, String> param) {
                return new TableCell<Payment, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            if ("OUTSTANDING".equals(item)) {
                                setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-alignment: center; -fx-padding: 2px 6px;");
                            } else if ("PAID".equals(item)) {
                                setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-alignment: center; -fx-padding: 2px 6px;");
                            } else {
                                setStyle("");
                            }
                        }
                    }
                };
            }
        };
    }
}

