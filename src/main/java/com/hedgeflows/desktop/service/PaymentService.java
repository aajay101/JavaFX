package com.hedgeflows.desktop.service;

import com.hedgeflows.desktop.model.Payment;
import com.hedgeflows.desktop.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentService {
    private PaymentRepository paymentRepository;

    public PaymentService() {
        this.paymentRepository = new PaymentRepository();
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getOutstandingPayments() {
        return paymentRepository.findAll().stream()
                .filter(payment -> "OUTSTANDING".equals(payment.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Payment> getPaidPayments() {
        return paymentRepository.findAll().stream()
                .filter(payment -> "PAID".equals(payment.getStatus()))
                .collect(Collectors.toList());
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(String id) {
        // Implementation would depend on how IDs are handled
        // For now, we'll leave this as a placeholder
    }
}