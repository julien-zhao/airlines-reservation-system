package org.example.searchservice.controller;

import com.example.flightservice.entity.Flight;
import org.example.searchservice.client.FlightServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private FlightServiceClient flightServiceClient;

    @GetMapping("/flightsByOrigin")
    public List<Flight> searchFlightsByOrigin(@RequestParam String origin) {
        return flightServiceClient.getFlightsByOrigin(origin);
    }

    @GetMapping("/flightsByDestination")
    public List<Flight> searchFlightsByDestination(@RequestParam String destination) {
        return flightServiceClient.getFlightsByDestination(destination);
    }

    @GetMapping("/flightsByOriginAndDestination")
    public List<Flight> searchFlights(@RequestParam String origin,
                                      @RequestParam String destination) {
        return flightServiceClient.getFlightsByOriginAndDestination(origin, destination);
    }

    @GetMapping("/advancedSearch")
    public List<Flight> advancedSearch(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String departureTime,
            @RequestParam String arrivalTime) {

        return flightServiceClient.searchFlights(
                origin, destination, departureTime, arrivalTime
        );
    }


}
