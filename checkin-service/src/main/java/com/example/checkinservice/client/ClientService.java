package com.example.checkinservice.client;

import com.example.clientservice.entity.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-service", path ="/api/clients")
public interface ClientService {

    @GetMapping("/{id}")
    Client getClientById(@PathVariable Long id);

}
