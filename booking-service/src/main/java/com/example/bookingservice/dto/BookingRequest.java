package com.example.bookingservice.dto;

import com.example.bookingservice.entity.Passenger;
import com.example.paymentservice.entity.Payment;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    private List<Passenger> passengers;
    private Payment payment;
}