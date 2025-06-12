package com.example.bookingservice.client;

import com.example.clientservice.entity.Client;
import com.example.paymentservice.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", path ="/api/payments")

public interface PaymentServiceClient {
    @GetMapping("/{id}")
    Payment getPaymentById(@PathVariable Long id);

    @PostMapping
    Payment save(@RequestBody Payment payment);


}
