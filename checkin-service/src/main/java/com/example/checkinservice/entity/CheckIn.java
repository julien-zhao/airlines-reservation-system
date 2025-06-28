package com.example.checkinservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "check_in")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "airline")
    private String airline;

    @Column(name = "address")
    private String address;

    @Column(name = "terminal")
    private String terminal;

    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;


    @Column(name = "is_confirmed")
    private Boolean isConfirmed;

}
