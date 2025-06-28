package com.example.flightservice.service;

import com.example.flightservice.entity.Flight;
import com.example.flightservice.entity.Layover;
import com.example.flightservice.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }


    @Override
    public List<Flight> getFlightsByOrigin(String origin) {
        return flightRepository.getFlightsByOrigin(origin);
    }

    @Override
    public List<Flight> getFlightsByDestination(String destination) {
        return flightRepository.getFlightsByDestination(destination);
    }

    @Override
    public List<Flight> getFlightsByOriginAndDestination(String origin, String destination) {
        return flightRepository.getFlightsByOriginAndDestination(origin, destination);
    }




    @Override
    public List<Flight> searchFlights(String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        return flightRepository.getFlights(origin, destination, departureTime, arrivalTime);
    }
}
