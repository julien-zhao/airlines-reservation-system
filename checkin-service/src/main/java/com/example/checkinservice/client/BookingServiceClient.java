package com.example.checkinservice.client;

import com.example.bookingservice.entity.Booking;
import com.example.checkinservice.dto.PassengerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "booking-service", path = "/api/bookings")
public interface BookingServiceClient {
    @GetMapping("/{id}")
    Booking getBooking(@PathVariable Long id);

    @GetMapping("/{bookingId}/passengers")
    List<PassengerDTO> getPassengersByBooking(@PathVariable("bookingId") Long bookingId);
}
