package com.example.bookingservice.service;

import com.example.bookingservice.client.ClientService;
import com.example.bookingservice.client.FlightServiceClient;
import com.example.bookingservice.client.NotificationServiceClient;
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
    private final NotificationServiceClient notificationServiceClient;
    public BookingService(BookingRepository bookingRepository, FlightServiceClient flightServiceClient, PaymentServiceClient paymentServiceClient, ClientService clientService, NotificationServiceClient notificationServiceClient) {
        this.bookingRepository = bookingRepository;
        this.flightServiceClient = flightServiceClient;
        this.paymentServiceClient = paymentServiceClient;
        this.clientService = clientService;
        this.notificationServiceClient = notificationServiceClient;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "price is not correct");
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

        booking.setAirline(flightServiceClient.getFlight(flightId).getAirline());
        booking.setAddress(flightServiceClient.getFlight(flightId).getAddress());
        booking.setTerminal(flightServiceClient.getFlight(flightId).getTerminal());
        booking.setFlightNumber(flightServiceClient.getFlight(flightId).getFlightNumber());
        booking.setOrigin(flightServiceClient.getFlight(flightId).getOrigin());
        booking.setDestination(flightServiceClient.getFlight(flightId).getDestination());
        booking.setDepartureTime(flightServiceClient.getFlight(flightId).getDepartureTime());
        booking.setArrivalTime(flightServiceClient.getFlight(flightId).getArrivalTime());


        booking.setPassengerCount(passengerCount);
        booking.setBookingDate(LocalDateTime.now());

        for (Passenger p : passengers) {
            p.setBooking(booking);
        }

        booking.setPassengers(passengers);


        booking.setConfirmed(true);


        String clientEmail = clientService.getClientById(booking.getUserId()).getEmail();

        notificationServiceClient.sendEmail(clientEmail,
                "Confirmation Réservation",
                "Votre réservation a été effectué avec succès.");


        return bookingRepository.save(booking);
    }



}
