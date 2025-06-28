package com.example.checkinservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckInRequest {
    private Long bookingId;
    private List<CheckInRequestItem> passengers;
}