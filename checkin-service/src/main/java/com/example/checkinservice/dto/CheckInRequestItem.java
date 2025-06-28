package com.example.checkinservice.dto;


import lombok.Data;

@Data
public class CheckInRequestItem {
    private String firstName;
    private String lastName;
    private String passportNumber;
    private Double luggageWeight;
}