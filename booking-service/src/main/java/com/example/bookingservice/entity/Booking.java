package com.example.bookingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_id", nullable = false)
    private Long flightId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;


    @Column(name = "airline")
    private String airline;

    @Column(name = "address")
    private String address;

    @Column(name = "terminal")
    private String terminal;

    @Column(name = "flight_number")
    private String flightNumber;

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


    @Column(name = "passenger_count", nullable = false)
    private int passengerCount;

    @Column(name = "confirmed", nullable = false)
    private boolean confirmed = false;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Passenger> passengers;





}
