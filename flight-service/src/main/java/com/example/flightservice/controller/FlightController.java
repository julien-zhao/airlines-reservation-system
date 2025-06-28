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
    public ResponseEntity<?> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/searchByOrigin")
    public ResponseEntity<?> getFlightsByOrigin(@RequestParam String origin) {
        List<Flight> flights = flightService.getFlightsByOrigin(origin);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/searchByDestination")
    public ResponseEntity<?> getFlightsByDestination(@RequestParam String destination) {
        List<Flight> flights = flightService.getFlightsByDestination(destination);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/searchByOriginAndDestination")
    public ResponseEntity<?> getFlightsByOriginAndDestination(@RequestParam String origin,
                                                              @RequestParam String destination) {
        List<Flight> flights = flightService.getFlightsByOriginAndDestination(origin, destination);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFlights(@RequestParam String origin,
                                           @RequestParam String destination,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime) {
        List<Flight> flights = flightService.searchFlights(origin, destination, departureTime, arrivalTime);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{flightId}/availability")
    public ResponseEntity<?> getAvailableSeats(@PathVariable Long flightId) {
        Flight flight = flightService.getFlightById(flightId);
        return ResponseEntity.ok(flight.getAvailableSeats());
    }


    @PutMapping("/{flightId}/decrement")
    public ResponseEntity<?> decrementSeats(@PathVariable Long flightId, @RequestParam int count) {
        Flight flight = flightService.getFlightById(flightId);
        if (flight == null) {
            return ResponseEntity.ok("Flight not found.");
        }

        if (count < 0) {
            return ResponseEntity.ok("Count must be greater than zero.");
        }

        if (flight.getAvailableSeats() < count) {
            return ResponseEntity.ok("Not enough available seats.");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - count);
        flightService.saveFlight(flight);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/capacity")
    public ResponseEntity<?> getFlightCapacity(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found");
        }
        return ResponseEntity.ok(flight.getCapacity());
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Flight deleted successfully.");
    }

}
