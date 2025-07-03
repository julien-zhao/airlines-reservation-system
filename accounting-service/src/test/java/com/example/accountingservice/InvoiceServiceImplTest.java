package com.example.accountingservice;

import com.example.accountingservice.entity.Invoice;
import com.example.accountingservice.repository.InvoiceRepository;
import com.example.accountingservice.service.InvoiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveInvoice_shouldSetIssuedAtAndStatus_thenSave() {
        Invoice invoice = new Invoice();
        invoice.setStatus(null);
        invoice.setIssuedAt(null);

        Invoice savedInvoice = new Invoice();
        savedInvoice.setId(1L);

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(savedInvoice);

        Invoice result = invoiceService.saveInvoice(invoice);

        assertNotNull(result);
        verify(invoiceRepository).save(invoice);

        assertNotNull(invoice.getIssuedAt());
        assertEquals("ISSUED", invoice.getStatus());
    }

    @Test
    void getAllInvoices_shouldReturnList() {
        List<Invoice> invoices = List.of(new Invoice(), new Invoice());
        when(invoiceRepository.findAll()).thenReturn(invoices);

        List<Invoice> result = invoiceService.getAllInvoices();

        assertEquals(2, result.size());
        assertEquals(invoices, result);
    }

    @Test
    void getInvoiceById_shouldReturnInvoice_whenFound() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        Invoice result = invoiceService.getInvoiceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getInvoiceById_shouldReturnNull_whenNotFound() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());

        Invoice result = invoiceService.getInvoiceById(1L);

        assertNull(result);
    }
}
