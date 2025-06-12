package com.example.bookingservice.client;

import com.example.flightservice.entity.Flight;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "flight-service", path ="/api/flights")
public interface FlightServiceClient {

    @GetMapping("/{flightId}")
    Flight getFlight(@PathVariable Long flightId);

    @GetMapping("/{flightId}/availability")
    int getAvailableSeats(@PathVariable Long flightId);

    @PutMapping("/{flightId}/decrement")
    void decreaseSeats(@PathVariable("flightId") Long flightId, @RequestParam int count);
}
