package com.example.checkinservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service", path = "/api/flights")
public interface FlightServiceClient {

    @GetMapping("/{flightId}/capacity")
    int getFlightCapacity(@PathVariable("flightId") Long flightId);
}
