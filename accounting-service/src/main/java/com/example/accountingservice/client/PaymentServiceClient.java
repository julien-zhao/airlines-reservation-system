package com.example.accountingservice.client;

import com.example.accountingservice.dto.PaymentDTO;
//import com.example.paymentservice.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-service", path = "/api/payments")
public interface PaymentServiceClient {
    @GetMapping("/{id}")
    PaymentDTO getPaymentById(@PathVariable Long id);
}