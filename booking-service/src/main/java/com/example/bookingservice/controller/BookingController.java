package com.example.bookingservice.controller;

import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.dto.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 创建订单前先调用 inventory-service 判断是否还有可用座位
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        // 构建调用 inventory 的请求地址
        String url = "http://inventory-service/inventory/" +
                booking.getFlightNumber() + "/" +
                booking.getFlightDate();

        // 通过 RestTemplate 发起 GET 请求，获取库存信息
        InventoryDTO inventory = restTemplate.getForObject(url, InventoryDTO.class);

        // 如果没有库存或已满，返回错误响应
        if (inventory == null || inventory.getAvailableSeats() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("There are no remaining seats on this flight and it cannot be booked.");
        }

        // 3. 有座位，保存订单
        Booking saved = repository.save(booking);

        // 4. 调用 inventory-service 扣减座位数
        String updateUrl = "http://inventory-service/inventory/update";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("flightNumber", booking.getFlightNumber());
        requestBody.put("flightDate", booking.getFlightDate());

        restTemplate.put(updateUrl, requestBody);

        // 5. 返回保存的订单
        return ResponseEntity.ok(saved);

    }

    /**
     * 获取所有订单记录
     */
    @GetMapping
    public List<Booking> getAllBookings() {
        return repository.findAll();
    }
}
