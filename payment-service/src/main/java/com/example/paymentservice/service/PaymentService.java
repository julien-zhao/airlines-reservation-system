package com.example.paymentservice.service;

import com.example.paymentservice.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment save(Payment payment);
    Payment getPaymentById(Long id);
    List<Payment> getPayments();
    void deletePayment(Long id);
}
