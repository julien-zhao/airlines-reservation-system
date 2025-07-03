package com.example.checkinservice;

import com.example.checkinservice.client.BookingServiceClient;
import com.example.checkinservice.client.ClientService;
import com.example.checkinservice.client.FlightServiceClient;
import com.example.checkinservice.client.NotificationServiceClient;
import com.example.checkinservice.dto.CheckInRequest;
import com.example.checkinservice.dto.CheckInRequestItem;
import com.example.checkinservice.dto.CheckInResponse;
import com.example.checkinservice.dto.PassengerDTO;
import com.example.checkinservice.entity.CheckIn;
import com.example.checkinservice.repository.CheckInRepository;
import com.example.checkinservice.service.CheckInService;
import com.example.clientservice.entity.Client;
import com.example.bookingservice.entity.Booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckInServiceTest {

    @Mock
    private CheckInRepository checkInRepository;

    @Mock
    private FlightServiceClient flightServiceClient;

    @Mock
    private BookingServiceClient bookingServiceClient;

    @Mock
    private ClientService clientService;

    @Mock
    private NotificationServiceClient notificationServiceClient;

    @InjectMocks
    private CheckInService checkInService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processGroupCheckIn_success() {
        Long bookingId = 1L;
        Long flightId = 100L;
        Long userId = 10L;

        CheckInRequestItem passengerRequest = new CheckInRequestItem();
        passengerRequest.setPassportNumber("P123");
        passengerRequest.setFirstName("John");
        passengerRequest.setLastName("Doe");
        passengerRequest.setLuggageWeight(20.0);

        CheckInRequest request = new CheckInRequest();
        request.setBookingId(bookingId);
        request.setPassengers(List.of(passengerRequest));

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setFlightId(flightId);
        booking.setUserId(userId);
        booking.setAirline("AirTest");
        booking.setAddress("AddressTest");
        booking.setTerminal("T1");
        booking.setOrigin("CityA");
        booking.setDestination("CityB");
        booking.setDepartureTime(LocalDateTime.now().plusDays(1));
        booking.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));

        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setPassportNumber("P123");
        passengerDTO.setFirstName("John");
        passengerDTO.setLastName("Doe");
        passengerDTO.setSeatNumber(null);  // no seat assigned yet

        List<PassengerDTO> passengers = List.of(passengerDTO);

        Client client = new Client();
        client.setId(userId);
        client.setEmail("john.doe@example.com");

        when(bookingServiceClient.getBooking(bookingId)).thenReturn(booking);
        when(bookingServiceClient.getPassengersByBooking(bookingId)).thenReturn(passengers);
        when(flightServiceClient.getFlightCapacity(flightId)).thenReturn(200);
        when(checkInRepository.findById(bookingId)).thenReturn(Optional.empty());
        when(checkInRepository.save(any(CheckIn.class))).thenAnswer(i -> i.getArgument(0));
        when(clientService.getClientById(userId)).thenReturn(client);
        when(notificationServiceClient.sendEmail(anyString(), anyString(), anyString())).thenReturn("Sent");

        CheckInResponse response = checkInService.processGroupCheckIn(request);

        assertNotNull(response);
        assertNotNull(response.getCheckIn());
        assertEquals(1, response.getPassengers().size());

        PassengerDTO checkedInPassenger = response.getPassengers().get(0);
        assertEquals("P123", checkedInPassenger.getPassportNumber());
        assertEquals("John", checkedInPassenger.getFirstName());
        assertTrue(checkedInPassenger.getSeatNumber() != null && !checkedInPassenger.getSeatNumber().isEmpty());
        assertEquals(20.0, checkedInPassenger.getLuggageWeight());
        assertTrue(checkedInPassenger.getIsConfirmed());

        verify(checkInRepository).save(any(CheckIn.class));
        verify(notificationServiceClient).sendEmail(eq(client.getEmail()), eq("Confirmation Check-in"), anyString());
    }



    @Test
    void processGroupCheckIn_passengerNotFound_shouldThrow() {
        Long bookingId = 1L;
        Long flightId = 100L;

        CheckInRequestItem item = new CheckInRequestItem();
        item.setPassportNumber("P999");
        item.setFirstName("Alice");
        item.setLastName("Smith");
        item.setLuggageWeight(10.0);

        CheckInRequest request = new CheckInRequest();
        request.setBookingId(bookingId);
        request.setPassengers(List.of(item));

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setFlightId(flightId);

        PassengerDTO existingPassenger = new PassengerDTO();
        existingPassenger.setPassportNumber("P123");
        existingPassenger.setFirstName("John");
        existingPassenger.setLastName("Doe");

        when(bookingServiceClient.getBooking(bookingId)).thenReturn(booking);
        when(bookingServiceClient.getPassengersByBooking(bookingId)).thenReturn(List.of(existingPassenger));
        when(flightServiceClient.getFlightCapacity(flightId)).thenReturn(200);
        when(checkInRepository.findById(bookingId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> checkInService.processGroupCheckIn(request));
        assertTrue(ex.getMessage().contains("Passenger with passport P999 and name Alice Smith not found."));
    }


}
