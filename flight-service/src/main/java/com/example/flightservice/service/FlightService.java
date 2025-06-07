package com.example.flightservice.service;

import com.example.flightservice.entity.Flight;

import java.util.List;

public interface FlightService {
    Flight saveFlight(Flight flight);
    List<Flight> getAllFlights();
    Flight getFlightById(Long id);
    void deleteFlight(Long id);
}