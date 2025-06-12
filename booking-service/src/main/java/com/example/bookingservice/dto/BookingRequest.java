package com.example.bookingservice.dto;

import com.example.bookingservice.entity.Passenger;
import com.example.paymentservice.entity.Payment;

import java.util.List;

public class BookingRequest {
    private List<Passenger> passengers;
    private Payment payment;

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}