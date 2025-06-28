package com.example.bookingservice.controller;

import com.example.bookingservice.client.PaymentServiceClient;
import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.Passenger;
import com.example.bookingservice.service.BookingService;
import com.example.paymentservice.entity.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getBookings() {
        return bookingService.getBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No booking found"));
        }
        return ResponseEntity.ok(booking);
    }



    @PostMapping("/create")
    public Booking createBooking(@RequestParam Long flightId,
                                 @RequestParam Long userId,
                                 @RequestBody BookingRequest request) {
        return bookingService.createBooking(flightId, userId, request.getPassengers(), request.getPayment());
    }

    @GetMapping("/{bookingId}/passengers")
    public ResponseEntity<List<Passenger>> getPassengersByBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Passenger> passengers = booking.getPassengers()
                .stream()
                .map(p -> {
                    Passenger dto = new Passenger();
                    dto.setId(p.getId());
                    dto.setFirstName(p.getFirstName());
                    dto.setLastName(p.getLastName());
                    dto.setPassportNumber(p.getPassportNumber());
                    dto.setBooking(p.getBooking());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(passengers);
    }



}
