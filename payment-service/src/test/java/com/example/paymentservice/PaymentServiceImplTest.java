package com.example.paymentservice;


import com.example.clientservice.entity.Client;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private com.example.paymentservice.client.ClientService clientService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Payment createValidPayment() {
        Payment payment = new Payment();
        payment.setUserId(1L);
        payment.setCardNumber("1234567812345678");
        payment.setCvv("123");
        payment.setExpiryDate("12/25");
        payment.setPrice(100);
        return payment;
    }

    private Client createClient() {
        Client client = new Client();
        client.setId(1L);
        client.setEmail("test@example.com");
        return client;
    }

    @Test
    void save_shouldThrow_whenUserNotFound() {
        Payment payment = createValidPayment();
        when(clientService.getClientById(payment.getUserId())).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            paymentService.save(payment);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("user not found", exception.getReason());
    }

    @Test
    void save_shouldThrow_whenCardNumberInvalid() {
        Payment payment = createValidPayment();
        payment.setCardNumber("1234"); // invalid length
        when(clientService.getClientById(payment.getUserId())).thenReturn(createClient());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.save(payment);
        });

        assertEquals("Card number must be exactly 16 digits.", exception.getMessage());
    }

    @Test
    void save_shouldThrow_whenCvvInvalid() {
        Payment payment = createValidPayment();
        payment.setCvv("12"); // invalid length
        when(clientService.getClientById(payment.getUserId())).thenReturn(createClient());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.save(payment);
        });

        assertEquals("CVV must be exactly 3 digits.", exception.getMessage());
    }

    @Test
    void save_shouldThrow_whenExpiryDateInvalid() {
        Payment payment = createValidPayment();
        payment.setExpiryDate("1234"); // invalid format
        when(clientService.getClientById(payment.getUserId())).thenReturn(createClient());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.save(payment);
        });

        assertEquals("Expiry date must be in the format MM/YY.", exception.getMessage());
    }

    @Test
    void save_shouldSave_whenValidPayment() {
        Payment payment = createValidPayment();
        when(clientService.getClientById(payment.getUserId())).thenReturn(createClient());
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment saved = paymentService.save(payment);

        assertNotNull(saved);
        verify(paymentRepository).save(payment);
    }

    @Test
    void getPaymentById_shouldReturnPayment_whenFound() {
        Payment payment = createValidPayment();
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentById(1L);

        assertNotNull(result);
        assertEquals(payment, result);
    }

    @Test
    void getPaymentById_shouldReturnNull_whenNotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        Payment result = paymentService.getPaymentById(1L);

        assertNull(result);
    }

    @Test
    void getPayments_shouldReturnList() {
        List<Payment> payments = List.of(createValidPayment());
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getPayments();

        assertEquals(1, result.size());
        assertEquals(payments, result);
    }

    @Test
    void deletePayment_shouldThrow_whenNotFound() {
        when(paymentRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            paymentService.deletePayment(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Payment not found", exception.getReason());
    }

    @Test
    void deletePayment_shouldDelete_whenFound() {
        when(paymentRepository.existsById(1L)).thenReturn(true);

        paymentService.deletePayment(1L);

        verify(paymentRepository).deleteById(1L);
    }
}
