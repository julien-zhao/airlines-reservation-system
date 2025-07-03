package com.example.accountingservice.service;

import com.example.accountingservice.client.PaymentServiceClient;
import com.example.accountingservice.dto.PaymentDTO;
import com.example.accountingservice.entity.Invoice;
import com.example.accountingservice.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentServiceClient paymentServiceClient;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, PaymentServiceClient paymentServiceClient) {
        this.invoiceRepository = invoiceRepository;
        this.paymentServiceClient = paymentServiceClient;
    }

    @Override
    public Invoice generateInvoice(Long paymentId) {
        PaymentDTO payment = paymentServiceClient.getPaymentById(paymentId);

        Invoice invoice = new Invoice();
        invoice.setPaymentId(paymentId);
        invoice.setClientId(payment.getUserId());
        invoice.setAmount(BigDecimal.valueOf(payment.getPrice()));
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setStatus("PAID");

        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }
}
