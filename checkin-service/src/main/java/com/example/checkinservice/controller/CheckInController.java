package com.example.checkinservice.controller;

import com.example.checkinservice.model.CheckIn;
import com.example.checkinservice.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checkin")
public class CheckInController {

    @Autowired
    private CheckInRepository repository;

    // 创建新的值机记录
    @PostMapping
    public CheckIn create(@RequestBody CheckIn checkIn) {
        checkIn.setCheckedInAt(LocalDateTime.now()); // 自动设置值机时间
        return repository.save(checkIn);
    }

    // 获取所有值机记录
    @GetMapping
    public List<CheckIn> getAll() {
        return repository.findAll();
    }

    // 根据 bookingId 查询是否值机
    @GetMapping("/{bookingId}")
    public Optional<CheckIn> getByBookingId(@PathVariable Long bookingId) {
        return repository.findByBookingId(bookingId);
    }
}
