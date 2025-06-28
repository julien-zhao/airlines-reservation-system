package com.example.checkinservice.controller;

import com.example.checkinservice.dto.CheckInRequest;
import com.example.checkinservice.dto.CheckInResponse;
import com.example.checkinservice.entity.CheckIn;
import com.example.checkinservice.service.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }


    @PostMapping
    public ResponseEntity<CheckInResponse> checkInPassengers(@RequestBody CheckInRequest request) {
        CheckInResponse result = checkInService.processGroupCheckIn(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<CheckIn>> getAllCheckIns() {
        List<CheckIn> allCheckIns = checkInService.getAllCheckIns();
        return ResponseEntity.ok(allCheckIns);
    }

    @GetMapping("/{checkInId}")
    public ResponseEntity<?> getCheckInById(@PathVariable Long checkInId) {
        return checkInService.getCheckInById(checkInId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<CheckIn>> getCheckInsByFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(checkInService.getCheckInsByFlightId(flightId));
    }



    @GetMapping("/responses")
    public ResponseEntity<List<CheckInResponse>> getAllCheckInsResponse() {
        List<CheckInResponse> checkIns = checkInService.getAllCheckInResponses();
        return ResponseEntity.ok(checkIns);
    }


    @GetMapping("/response/{id}")
    public ResponseEntity<CheckInResponse> getCheckInResponseById(@PathVariable Long id) {
        return checkInService.getCheckInResponseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
