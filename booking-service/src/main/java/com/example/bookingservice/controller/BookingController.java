package com.example.bookingservice.controller;

import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository repository;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return repository.save(booking);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return repository.findAll();
    }
}
