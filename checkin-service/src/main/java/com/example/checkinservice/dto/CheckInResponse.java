package com.example.checkinservice.dto;

import com.example.checkinservice.entity.CheckIn;
import lombok.Data;

import java.util.List;

@Data
public class CheckInResponse {
    private CheckIn checkIn;
    private List<PassengerDTO> passengers;
}
