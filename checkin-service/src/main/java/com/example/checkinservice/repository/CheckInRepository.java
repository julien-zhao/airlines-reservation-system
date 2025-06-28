package com.example.checkinservice.repository;

import com.example.checkinservice.dto.CheckInResponse;
import com.example.checkinservice.dto.PassengerDTO;
import com.example.checkinservice.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {


    List<CheckIn> findByFlightId(Long flightId);

    List<CheckIn> findByBookingId(Long bookingId);


    Optional<CheckIn> findById(Long id);

}
