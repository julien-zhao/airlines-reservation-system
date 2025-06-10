package com.example.flightservice.repository;

import com.example.flightservice.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> getFlightsByOrigin(String origin);

    List<Flight> getFlightsByDestination(String destination);

    List<Flight> getFlightsByOriginAndDestination(String origin, String destination);


    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination " +
            "AND f.departureTime >= :departureTime AND f.arrivalTime <= :arrivalTime")
    List<Flight> getFlights(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("departureTime") LocalDateTime departureTime,
            @Param("arrivalTime") LocalDateTime arrivalTime);

}