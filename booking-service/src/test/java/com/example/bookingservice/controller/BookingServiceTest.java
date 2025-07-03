package com.example.bookingservice.controller;


import com.example.accountingservice.entity.Invoice;
import com.example.bookingservice.client.*;
import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.Passenger;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.service.BookingService;
import com.example.paymentservice.entity.Payment;
import com.example.clientservice.entity.Client;
import com.example.flightservice.entity.Flight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private FlightServiceClient flightServiceClient;
    @Mock
    private PaymentServiceClient paymentServiceClient;
    @Mock
    private ClientService clientService;
    @Mock
    private NotificationServiceClient notificationServiceClient;
    @Mock
    private AccountingServiceClient accountingServiceClient;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void createBooking_flightNotFound_shouldThrow() {
        when(flightServiceClient.getFlight(1L)).thenReturn(null);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> bookingService.createBooking(1L, 1L, List.of(), new Payment()));
        assertEquals("404 NOT_FOUND \"flight not found\"", exception.getMessage());
    }

    @Test
    void createBooking_userNotFound_shouldThrow() {
        Flight flight = new Flight();
        flight.setPrice(100);
        when(flightServiceClient.getFlight(1L)).thenReturn(flight);
        when(clientService.getClientById(1L)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> bookingService.createBooking(1L, 1L, List.of(), new Payment()));
        assertEquals("404 NOT_FOUND \"user not found\"", exception.getMessage());
    }

    @Test
    void createBooking_priceMismatch_shouldThrow() {
        Flight flight = new Flight();
        flight.setPrice(100);
        when(flightServiceClient.getFlight(1L)).thenReturn(flight);
        when(clientService.getClientById(1L)).thenReturn(new Client());

        Payment payment = new Payment();
        payment.setPrice(50);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> bookingService.createBooking(1L, 1L, List.of(), payment));
        assertEquals("400 BAD_REQUEST \"price is not correct\"", exception.getMessage());
    }

    @Test
    void createBooking_notEnoughSeats_shouldThrow() {
        Flight flight = new Flight();
        flight.setPrice(100);
        when(flightServiceClient.getFlight(1L)).thenReturn(flight);
        when(clientService.getClientById(1L)).thenReturn(new Client());
        when(flightServiceClient.getAvailableSeats(1L)).thenReturn(0);

        Payment payment = new Payment();
        payment.setPrice(100);

        List<Passenger> passengers = List.of(new Passenger());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> bookingService.createBooking(1L, 1L, passengers, payment));
        assertEquals("Not enough seats available", exception.getMessage());
    }
}
