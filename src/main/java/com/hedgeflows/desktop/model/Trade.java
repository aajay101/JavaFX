package com.hedgeflows.desktop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class Trade extends BaseEntity {
    private String currencyPair;
    private Double amount;
    private String type; // BUY/SELL
    private String status; // OPEN/CLOSED
    private LocalDate date;

    public Trade() {
        super();
    }

    public Trade(UUID id, LocalDateTime createdAt, String currencyPair, Double amount, String type, String status, LocalDate date) {
        super(id, createdAt);
        this.currencyPair = currencyPair;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.date = date;
    }

    public Trade(String currencyPair, Double amount, String type, String status, LocalDate date) {
        super();
        this.currencyPair = currencyPair;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.date = date;
    }
}

