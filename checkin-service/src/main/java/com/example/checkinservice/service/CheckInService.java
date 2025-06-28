package com.example.checkinservice.service;

import com.example.checkinservice.client.ClientService;
import com.example.checkinservice.client.BookingServiceClient;
import com.example.checkinservice.client.FlightServiceClient;
import com.example.checkinservice.client.NotificationServiceClient;
import com.example.checkinservice.dto.CheckInRequest;
import com.example.checkinservice.dto.CheckInRequestItem;
import com.example.checkinservice.dto.CheckInResponse;
import com.example.checkinservice.dto.PassengerDTO;
import com.example.checkinservice.entity.CheckIn;
import com.example.checkinservice.repository.CheckInRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final FlightServiceClient flightServiceClient;
    private final BookingServiceClient bookingServiceClient;
    private final ClientService clientService;
    private final NotificationServiceClient notificationServiceClient;


    public CheckInService(CheckInRepository checkInRepository,
                          FlightServiceClient flightServiceClient,
                          BookingServiceClient bookingServiceClient,
                          ClientService clientService,
                          NotificationServiceClient notificationServiceClient) {
        this.checkInRepository = checkInRepository;
        this.flightServiceClient = flightServiceClient;
        this.bookingServiceClient = bookingServiceClient;
        this.clientService = clientService;
        this.notificationServiceClient = notificationServiceClient;
    }

    public List<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }

    public Optional<CheckIn> getCheckInById(Long id) {
        return checkInRepository.findById(id);
    }


    public List<CheckIn> getCheckInsByFlightId(Long flightId) {
        return checkInRepository.findByFlightId(flightId);
    }



    public List<CheckInResponse> getAllCheckInResponses() {
        List<CheckIn> checkIns = checkInRepository.findAll();

        return checkIns.stream()
                .map(this::mapToCheckInResponse)
                .collect(Collectors.toList());
    }


    public Optional<CheckInResponse> getCheckInResponseById(Long id) {
        return checkInRepository.findById(id)
                .map(this::mapToCheckInResponse);
    }


    private CheckInResponse mapToCheckInResponse(CheckIn checkIn) {
        Long bookingId = checkIn.getBookingId();

        List<PassengerDTO> passengers = bookingServiceClient.getPassengersByBooking(bookingId);

        CheckInResponse response = new CheckInResponse();
        response.setCheckIn(checkIn);
        response.setPassengers(passengers);

        return response;
    }



    public CheckInResponse processGroupCheckIn(CheckInRequest request) {
        Long bookingId = request.getBookingId();
        Long flightId = bookingServiceClient.getBooking(bookingId).getFlightId();
        int capacity = flightServiceClient.getFlightCapacity(flightId);

        List<PassengerDTO> allPassengers = bookingServiceClient.getPassengersByBooking(bookingId);

        Set<String> usedSeats = bookingServiceClient.getPassengersByBooking(bookingId).stream()
                .map(PassengerDTO::getSeatNumber)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());



        if (!checkInRepository.findById(bookingId).isEmpty()) {
            throw new RuntimeException("Already have checkIn");
        }
        List<PassengerDTO> checkedInPassengers = new ArrayList<>();

        for (CheckInRequestItem item : request.getPassengers()) {
            Optional<PassengerDTO> matchedPassenger = allPassengers.stream()
                    .filter(p -> p.getPassportNumber().equals(item.getPassportNumber())
                            && p.getFirstName().equalsIgnoreCase(item.getFirstName())
                            && p.getLastName().equalsIgnoreCase(item.getLastName()))
                    .findFirst();

            if (matchedPassenger.isEmpty()) {
                throw new RuntimeException("Passenger with passport " + item.getPassportNumber() +
                        " and name " + item.getFirstName() + " " + item.getLastName() + " not found.");
            }


            PassengerDTO passenger = matchedPassenger.get();

            String seatNumber = generateRandomSeat(usedSeats, capacity);
            usedSeats.add(seatNumber);

            passenger.setSeatNumber(seatNumber);
            passenger.setLuggageWeight(item.getLuggageWeight());
            passenger.setIsConfirmed(true);


            checkedInPassengers.add(passenger);
        }

        CheckIn checkIn = new CheckIn();
        checkIn.setBookingId(bookingId);
        checkIn.setAirline(bookingServiceClient.getBooking(bookingId).getAirline());
        checkIn.setAddress(bookingServiceClient.getBooking(bookingId).getAddress());
        checkIn.setTerminal(bookingServiceClient.getBooking(bookingId).getTerminal());
        checkIn.setFlightId(bookingServiceClient.getBooking(bookingId).getFlightId());
        checkIn.setOrigin(bookingServiceClient.getBooking(bookingId).getOrigin());
        checkIn.setDestination(bookingServiceClient.getBooking(bookingId).getDestination());
        checkIn.setDepartureTime(bookingServiceClient.getBooking(bookingId).getDepartureTime());
        checkIn.setArrivalTime(bookingServiceClient.getBooking(bookingId).getArrivalTime());
        checkIn.setIsConfirmed(true);

        checkIn = checkInRepository.save(checkIn);


        String clientEmail = clientService.getClientById(bookingServiceClient.getBooking(bookingId).getUserId()).getEmail();

        notificationServiceClient.sendEmail(clientEmail,
                "Confirmation Check-in",
                "Votre check-in a été effectué avec succès.");


        CheckInResponse response = new CheckInResponse();
        response.setCheckIn(checkIn);
        response.setPassengers(checkedInPassengers);

        return response;
    }


    private String generateRandomSeat(Set<String> usedSeats, int capacity) {
        if (usedSeats.size() >= capacity) {
            throw new RuntimeException("All seats are already taken.");
        }

        String seat;
        do {
            int randomSeat = ThreadLocalRandom.current().nextInt(1, capacity + 1);
            seat = String.valueOf(randomSeat);
        } while (usedSeats.contains(seat));

        return seat;
    }


}
