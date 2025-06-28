package org.example.searchservice.client;

import com.example.flightservice.entity.Flight;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "flight-service", path = "/api/flights")
public interface FlightServiceClient {

    @GetMapping
    List<Flight> getFlights();

    @GetMapping("/searchByOrigin")
    List<Flight> getFlightsByOrigin(@RequestParam("origin") String origin);

    @GetMapping("/searchByDestination")
    List<Flight> getFlightsByDestination(@RequestParam("destination") String destination);

    @GetMapping("/searchByOriginAndDestination")
    List<Flight> getFlightsByOriginAndDestination(@RequestParam("origin") String origin,
                                                  @RequestParam("destination") String destination);

    @GetMapping("/search")
    List<Flight> searchFlights(
            @RequestParam("origin") String origin,
            @RequestParam("destination") String destination,
            @RequestParam("departureTime") String departureTime,
            @RequestParam("arrivalTime") String arrivalTime
    );

}