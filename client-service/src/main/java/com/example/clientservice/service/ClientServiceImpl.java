package com.example.clientservice.service;

import com.example.clientservice.client.NotificationServiceClient;
import com.example.clientservice.entity.Client;
import com.example.clientservice.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final NotificationServiceClient notificationServiceClient;

    public ClientServiceImpl(ClientRepository clientRepository, NotificationServiceClient notificationServiceClient) {

        this.clientRepository = clientRepository;
        this.notificationServiceClient = notificationServiceClient;
    }

    @Override
    public Client getClientById(Long id){
        return clientRepository.findById(id).orElse(null);
    };

    @Override
    public Client saveClient(Client client){
        String clientEmail = client.getEmail();

        notificationServiceClient.sendEmail(clientEmail,
                "Confirmation Création de compte",
                "Votre création de compte a été effectué avec succès.");


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
