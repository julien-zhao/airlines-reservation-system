package com.example.checkinservice.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;
    private String seatNumber;
    private LocalDateTime checkedInAt;

    public CheckIn() {}

    public CheckIn(Long bookingId, String seatNumber, LocalDateTime checkedInAt) {
        this.bookingId = bookingId;
        this.seatNumber = seatNumber;
        this.checkedInAt = checkedInAt;
    }

    public Long getId() {
        return id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDateTime getCheckedInAt() {
        return checkedInAt;
    }

    public void setCheckedInAt(LocalDateTime checkedInAt) {
        this.checkedInAt = checkedInAt;
    }
}
