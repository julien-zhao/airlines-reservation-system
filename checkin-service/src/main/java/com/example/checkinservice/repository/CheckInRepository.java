package com.example.checkinservice.repository;

import com.example.checkinservice.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    Optional<CheckIn> findByBookingId(Long bookingId);
}
