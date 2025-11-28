package com.hedgeflows.desktop.repository;

import com.google.gson.reflect.TypeToken;
import com.hedgeflows.desktop.model.Payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository extends JsonRepository<Payment> {
    
    public PaymentRepository() {
        super("data/payments.json", new TypeToken<ArrayList<Payment>>() {}.getType());
    }

    @Override
    protected List<Payment> seedData() {
        List<Payment> payments = new ArrayList<>();
        // Add sample payments (3 Outstanding, 2 Paid)
        payments.add(new Payment("Amazon Web Services", 1250.75, "USD", LocalDate.now().plusDays(5), "OUTSTANDING"));
        payments.add(new Payment("Microsoft Azure", 2100.00, "USD", LocalDate.now().plusDays(10), "OUTSTANDING"));
        payments.add(new Payment("Google Cloud Platform", 950.50, "USD", LocalDate.now().plusDays(15), "OUTSTANDING"));
        payments.add(new Payment("Office 365 Subscription", 299.99, "USD", LocalDate.now().minusDays(20), "PAID"));
        payments.add(new Payment("Adobe Creative Suite", 599.99, "USD", LocalDate.now().minusDays(10), "PAID"));
        return payments;
    }
}