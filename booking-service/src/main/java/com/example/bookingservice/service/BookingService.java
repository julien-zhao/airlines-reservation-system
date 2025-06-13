package com.example.bookingservice.service;

import com.example.bookingservice.client.ClientService;
import com.example.bookingservice.client.FlightServiceClient;
import com.example.bookingservice.client.PaymentServiceClient;
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
    public BookingService(BookingRepository bookingRepository, FlightServiceClient flightServiceClient, PaymentServiceClient paymentServiceClient, ClientService clientService) {
        this.bookingRepository = bookingRepository;
        this.flightServiceClient = flightServiceClient;
        this.paymentServiceClient = paymentServiceClient;
        this.clientService = clientService;
    }

    public Booking getBooking(Long id) {
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "price is not correct");
        }
        payment.setUserId(userId);
        paymentServiceClient.save(payment);

        int available = flightServiceClient.getAvailableSeats(flightId);
        int passengerCount = passengers.size()+1;
        if (available < passengerCount) {
            throw new RuntimeException("Not enough seats available");
        }

        flightServiceClient.decreaseSeats(flightId, passengerCount);

        Booking booking = new Booking();
        booking.setFlightId(flightId);
        booking.setUserId(userId);
        booking.setPassengerCount(passengerCount);
        booking.setBookingDate(LocalDateTime.now());

        for (Passenger p : passengers) {
            p.setBooking(booking);
        }

        booking.setPassengers(passengers);


        booking.setConfirmed(true);
        return bookingRepository.save(booking);
    }




}
