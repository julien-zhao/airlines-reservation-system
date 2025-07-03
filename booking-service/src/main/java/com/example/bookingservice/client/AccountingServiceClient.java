package com.example.bookingservice.client;

import com.example.accountingservice.entity.Invoice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "accounting-service", path ="/api/invoices")
public interface AccountingServiceClient {

    @PostMapping
    Invoice saveInvoice(Invoice invoice);
}
