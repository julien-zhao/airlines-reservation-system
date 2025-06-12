package com.example.paymentservice.service;


import com.example.paymentservice.client.ClientService;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientService clientService;
    public PaymentServiceImpl(PaymentRepository paymentRepository,  ClientService clientService) {
        this.paymentRepository = paymentRepository;
        this.clientService = clientService;
    }

    @Override
    public Payment save(Payment payment) {
        validatePayment(payment);
        return paymentRepository.save(payment);
    }

    private void validatePayment(Payment payment) {
        if(clientService.getClientById(payment.getUserId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
        if (payment.getCardNumber() == null || !payment.getCardNumber().matches("\\d{16}")) {
            throw new IllegalArgumentException("Card number must be exactly 16 digits.");
        }
        if (payment.getCvv() == null || !payment.getCvv().matches("\\d{3}")) {
            throw new IllegalArgumentException("CVV must be exactly 3 digits.");
        }
        if (!isValidExpiryFormat(payment.getExpiryDate())) {
            throw new IllegalArgumentException("Expiry date must be in the format MM/YY.");
        }
    }

    private boolean isValidExpiryFormat(String expiryDate) {
        if (expiryDate == null) return false;
        return expiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$");
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    @Override
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        }
        paymentRepository.deleteById(id);
    }
}
