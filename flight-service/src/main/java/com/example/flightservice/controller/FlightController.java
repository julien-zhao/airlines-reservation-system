package com.example.flightservice.controller;

import com.example.flightservice.entity.Flight;
import com.example.flightservice.service.FlightService;
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
        return flightService.getFlightsByOrigin(origin);
    }

    @GetMapping("/searchByDestination")
    public List<Flight> getFlightsByDestination(@RequestParam String destination) {
        return flightService.getFlightsByDestination(destination);
    }

    @GetMapping("/searchByOriginAndDestination")
    public List<Flight> getFlightsByOriginAndDestination(@RequestParam String origin,
                                                         @RequestParam String destination) {
        return flightService.getFlightsByOriginAndDestination(origin, destination);
    }

    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam String origin,
                                      @RequestParam String destination,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime) {
        return flightService.searchFlights(origin, destination, departureTime, arrivalTime);
    }

    @GetMapping("/{flightId}/availability")
    public ResponseEntity<Integer> getAvailableSeats(@PathVariable Long flightId) {
        Flight flight = flightService.getFlightById(flightId);
        if(flight == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(flight.getAvailableSeats());
    }
    @PutMapping("/{flightId}/decrement")
    public ResponseEntity<?> decrementSeats(@PathVariable Long flightId, @RequestParam int count) {
        Flight flight = flightService.getFlightById(flightId);
        if(flight == null){
            return  ResponseEntity.notFound().build();
        }

        if(flight.getAvailableSeats() <= count){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough available seats");
        }

        if(count < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Count must be greater than zero.");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - count);
        flightService.saveFlight(flight);
        return ResponseEntity.ok().build();
    }


















    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
    }
}
