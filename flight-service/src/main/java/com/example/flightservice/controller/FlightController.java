package com.example.flightservice.controller;

import com.example.flightservice.entity.Flight;
import com.example.flightservice.repository.FlightRepository;
import com.example.flightservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.saveFlight(flight);
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }


    @GetMapping("/searchByOrigin")
    public List<Flight> getFlightsByOrigin(@RequestParam String origin) {
        return flightRepository.getFlightsByOrigin(origin);
    }

    @GetMapping("/searchByDestination")
    public List<Flight> getFlightsByDestination(@RequestParam String destination) {
        return flightRepository.getFlightsByDestination(destination);
    }

    @GetMapping("/searchByOriginAndDestination")
    public List<Flight> getFlightsByOriginAndDestination(@RequestParam String origin,
                                                         @RequestParam String destination) {
        return flightRepository.getFlightsByOriginAndDestination(origin, destination);
    }

    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime) {

        return flightRepository.getFlights(
                origin, destination, departureTime, arrivalTime);
    }



    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
    }
}
