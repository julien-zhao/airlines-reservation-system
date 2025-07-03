package com.example.flightservice;


import com.example.flightservice.entity.Flight;
import com.example.flightservice.repository.FlightRepository;
import com.example.flightservice.service.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightServiceImplTest {

    private FlightRepository flightRepository;
    private FlightServiceImpl flightService;

    @BeforeEach
    void setUp() {
        flightRepository = mock(FlightRepository.class);
        flightService = new FlightServiceImpl(flightRepository);
    }

    @Test
    void testSaveFlight() {
        Flight flight = new Flight();
        when(flightRepository.save(flight)).thenReturn(flight);

        Flight saved = flightService.saveFlight(flight);

        assertEquals(flight, saved);
        verify(flightRepository, times(1)).save(flight);
    }

    @Test
    void testGetAllFlights() {
        Flight f1 = new Flight();
        Flight f2 = new Flight();
        List<Flight> flights = Arrays.asList(f1, f2);

        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> result = flightService.getAllFlights();

        assertEquals(2, result.size());
        assertTrue(result.contains(f1));
        assertTrue(result.contains(f2));
    }

    @Test
    void testGetFlightById_found() {
        Flight flight = new Flight();
        flight.setId(1L);
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Flight result = flightService.getFlightById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetFlightById_notFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        Flight result = flightService.getFlightById(1L);

        assertNull(result);
    }

    @Test
    void testDeleteFlight() {
        Long id = 1L;
        doNothing().when(flightRepository).deleteById(id);

        flightService.deleteFlight(id);

        verify(flightRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetFlightsByOrigin() {
        String origin = "Paris";
        Flight flight = new Flight();
        List<Flight> flights = List.of(flight);

        when(flightRepository.getFlightsByOrigin(origin)).thenReturn(flights);

        List<Flight> result = flightService.getFlightsByOrigin(origin);

        assertEquals(flights, result);
    }

    @Test
    void testGetFlightsByDestination() {
        String destination = "London";
        Flight flight = new Flight();
        List<Flight> flights = List.of(flight);

        when(flightRepository.getFlightsByDestination(destination)).thenReturn(flights);

        List<Flight> result = flightService.getFlightsByDestination(destination);

        assertEquals(flights, result);
    }

    @Test
    void testGetFlightsByOriginAndDestination() {
        String origin = "Paris";
        String destination = "London";
        Flight flight = new Flight();
        List<Flight> flights = List.of(flight);

        when(flightRepository.getFlightsByOriginAndDestination(origin, destination)).thenReturn(flights);

        List<Flight> result = flightService.getFlightsByOriginAndDestination(origin, destination);

        assertEquals(flights, result);
    }

    @Test
    void testSearchFlights() {
        String origin = "Paris";
        String destination = "London";
        LocalDateTime departure = LocalDateTime.of(2025, 7, 1, 10, 0);
        LocalDateTime arrival = LocalDateTime.of(2025, 7, 1, 12, 0);

        Flight flight = new Flight();
        List<Flight> flights = List.of(flight);

        when(flightRepository.getFlights(origin, destination, departure, arrival)).thenReturn(flights);

        List<Flight> result = flightService.searchFlights(origin, destination, departure, arrival);

        assertEquals(flights, result);
    }
}
