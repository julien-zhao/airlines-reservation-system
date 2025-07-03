package com.example.accountingservice.service;

import com.example.accountingservice.entity.Invoice;
import com.example.accountingservice.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }


    @Override
    public Invoice saveInvoice(Invoice invoice) {
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setStatus("ISSUED");
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
