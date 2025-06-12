package com.example.clientservice.service;

import com.example.clientservice.entity.Client;
import com.example.clientservice.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getClientById(Long id){
        return clientRepository.findById(id).orElse(null);
    };

    @Override
    public Client saveClient(Client client){
        return clientRepository.save(client);
    };

    @Override
    public List<Client> getAllClient(){
        return clientRepository.findAll();
    };

    @Override
    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    };
}
