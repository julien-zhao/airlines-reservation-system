package com.example.inventoryservice.model;

import javax.persistence.*;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private String flightDate;
    private int availableSeats;

    public Inventory() {}

    public Inventory(String flightNumber, String flightDate, int availableSeats) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.availableSeats = availableSeats;
    }

    public Long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // 可选 setter（用于更新）
    public void setId(Long id) {
        this.id = id;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
