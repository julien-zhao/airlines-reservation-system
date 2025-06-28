package com.example.checkinservice.dto;

import com.example.bookingservice.entity.Booking;
import lombok.Data;

import javax.persistence.Column;


@Data
public class PassengerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String seatNumber;
    private Double luggageWeight;


    private Boolean isConfirmed;

}
