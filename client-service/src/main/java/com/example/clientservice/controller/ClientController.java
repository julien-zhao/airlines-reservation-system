package com.example.clientservice.controller;

import com.example.clientservice.model.Client;
import com.example.clientservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository repository;

    // 添加客户
    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return repository.save(client);
    }

    // 获取所有客户
    @GetMapping
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    // 获取单个客户
    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable Long id) {
        return repository.findById(id);
    }

    // 删除客户
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
