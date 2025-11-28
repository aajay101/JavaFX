package com.hedgeflows.desktop.model;

import lombok.Data;

@Data
public class Metric {
    private String title;
    private String value;
    private String trend;
    private boolean isPositive;

    public Metric() {
    }

    public Metric(String title, String value, String trend, boolean isPositive) {
        this.title = title;
        this.value = value;
        this.trend = trend;
        this.isPositive = isPositive;
    }
}