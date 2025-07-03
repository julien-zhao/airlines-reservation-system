package com.example.bookingservice.service;

import com.example.accountingservice.entity.Invoice;
import com.example.bookingservice.client.*;
import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.Passenger;
import com.example.bookingservice.repository.BookingRepository;

import com.example.paymentservice.entity.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightServiceClient flightServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final ClientService clientService;
    private final NotificationServiceClient notificationServiceClient;

    private final AccountingServiceClient  accountingServiceClient;


    public BookingService(BookingRepository bookingRepository, FlightServiceClient flightServiceClient, PaymentServiceClient paymentServiceClient, ClientService clientService, NotificationServiceClient notificationServiceClient, AccountingServiceClient accountingServiceClient) {
        this.bookingRepository = bookingRepository;
        this.flightServiceClient = flightServiceClient;
        this.paymentServiceClient = paymentServiceClient;
        this.clientService = clientService;
        this.notificationServiceClient = notificationServiceClient;


        this.accountingServiceClient = accountingServiceClient;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }





    public Booking createBooking(Long flightId, Long userId, List<Passenger> passengers, Payment payment) {
        if(flightServiceClient.getFlight(flightId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "flight not found");
        }
        if(clientService.getClientById(userId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
        if(payment.getPrice() != flightServiceClient.getFlight(flightId).getPrice()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price is not correct");
        }

        payment.setUserId(userId);
        paymentServiceClient.save(payment);

        int available = flightServiceClient.getAvailableSeats(flightId);
        int passengerCount = passengers.size();
        if (available < passengerCount) {
            throw new RuntimeException("Not enough seats available");
        }
        flightServiceClient.decreaseSeats(flightId, passengerCount);

        Booking booking = new Booking();
        booking.setFlightId(flightId);
        booking.setUserId(userId);

        var flight = flightServiceClient.getFlight(flightId);

        booking.setAirline(flight.getAirline());
        booking.setAddress(flight.getAddress());
        booking.setTerminal(flight.getTerminal());
        booking.setFlightNumber(flight.getFlightNumber());
        booking.setOrigin(flight.getOrigin());
        booking.setDestination(flight.getDestination());
        booking.setDepartureTime(flight.getDepartureTime());
        booking.setArrivalTime(flight.getArrivalTime());

        booking.setPassengerCount(passengerCount);
        booking.setBookingDate(LocalDateTime.now());

        for (Passenger p : passengers) {
            p.setBooking(booking);
        }
        booking.setPassengers(passengers);
        booking.setConfirmed(true);

        Booking savedBooking = bookingRepository.save(booking);

        // Création de la facture liée
        Invoice invoice = new Invoice();
        invoice.setBookingId(savedBooking.getId());
        invoice.setUserId(userId);
        invoice.setFlightId(flightId);
        invoice.setAirline(flight.getAirline());
        invoice.setAddress(flight.getAddress());
        invoice.setTerminal(flight.getTerminal());
        invoice.setFlightNumber(flight.getFlightNumber());
        invoice.setOrigin(flight.getOrigin());
        invoice.setDestination(flight.getDestination());
        invoice.setDepartureTime(flight.getDepartureTime());
        invoice.setArrivalTime(flight.getArrivalTime());
        invoice.setPassengerCount(passengerCount);
        invoice.setBookingDate(savedBooking.getBookingDate());
        invoice.setAmount(payment.getPrice());
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setStatus("ISSUED");

        accountingServiceClient.saveInvoice(invoice);

        String clientEmail = clientService.getClientById(booking.getUserId()).getEmail();

        notificationServiceClient.sendEmail(clientEmail,
                "Confirmation Réservation",
                "Votre réservation a été effectué avec succès.");

        return savedBooking;
    }




}
