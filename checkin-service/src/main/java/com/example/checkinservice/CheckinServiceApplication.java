package com.example.checkinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // 👉 让服务自动注册到 Eureka
public class CheckinServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckinServiceApplication.class, args);
    }
}

