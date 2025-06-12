package com.example.clientservice.service;

import com.example.clientservice.entity.Client;

import java.util.List;


public interface ClientService {
    Client getClientById(Long id);
    Client saveClient(Client client);
    List<Client> getAllClient();
    void deleteClient(Long id);
}
