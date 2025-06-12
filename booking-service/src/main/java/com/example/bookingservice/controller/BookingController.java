package com.example.bookingservice.controller;

import com.example.bookingservice.client.PaymentServiceClient;
import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.Passenger;
import com.example.bookingservice.service.BookingService;
import com.example.paymentservice.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        Booking booking = bookingService.getBooking(id);
        if(booking == null) {
            return ResponseEntity.ok().body("No booking found");
        }
        return ResponseEntity.ok(booking);
    }


    @PostMapping("/create")
    public Booking createBooking(@RequestParam Long flightId,
                                 @RequestParam Long userId,
                                 @RequestBody BookingRequest request) {
        return bookingService.createBooking(flightId, userId, request.getPassengers(), request.getPayment());
    }


}
