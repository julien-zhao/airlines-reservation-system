package com.example.flightservice.service;

import com.example.flightservice.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    Flight saveFlight(Flight flight);
    List<Flight> getAllFlights();
    Flight getFlightById(Long id);
    void deleteFlight(Long id);
    List<Flight> getFlightsByOrigin(String origin);
    List<Flight> getFlightsByDestination(String destination);
    List<Flight> getFlightsByOriginAndDestination(String origin, String destination);
    List<Flight> searchFlights(String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime);
}