// 5. Service Interface
package com.example.accountingservice.service;

import com.example.accountingservice.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice generateInvoice(Long paymentId);
    List<Invoice> getAllInvoices();
    Invoice getInvoiceById(Long id);
}