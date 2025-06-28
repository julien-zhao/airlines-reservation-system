package com.example.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", path = "/api/email")
public interface NotificationServiceClient {

    @PostMapping("/send")
    String sendEmail(@RequestParam("to") String to,
                     @RequestParam("subject") String subject,
                     @RequestParam("body") String body);
}

