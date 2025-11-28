package com.hedgeflows.desktop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity {
    private String beneficiary;
    private Double amount;
    private String currency;
    private LocalDate dueDate;
    private String status; // OUTSTANDING, PAID

    public Payment() {
        super();
    }

    public Payment(UUID id, LocalDateTime createdAt, String beneficiary, Double amount, String currency, LocalDate dueDate, String status) {
        super(id, createdAt);
        this.beneficiary = beneficiary;
        this.amount = amount;
        this.currency = currency;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Payment(String beneficiary, Double amount, String currency, LocalDate dueDate, String status) {
        super();
        this.beneficiary = beneficiary;
        this.amount = amount;
        this.currency = currency;
        this.dueDate = dueDate;
        this.status = status;
    }
}