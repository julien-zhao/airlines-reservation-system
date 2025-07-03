package com.example.clientservice;


import com.example.clientservice.client.NotificationServiceClient;
import com.example.clientservice.entity.Client;
import com.example.clientservice.repository.ClientRepository;
import com.example.clientservice.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private NotificationServiceClient notificationServiceClient;
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        notificationServiceClient = mock(NotificationServiceClient.class);
        clientService = new ClientServiceImpl(clientRepository, notificationServiceClient);
    }

    @Test
    void testGetClientById_found() {
        Client client = new Client();
        client.setId(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetClientById_notFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Client result = clientService.getClientById(1L);

        assertNull(result);
    }

    @Test
    void testSaveClient_sendsNotificationAndSaves() {
        Client client = new Client();
        client.setEmail("test@example.com");

        when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.saveClient(client);

        assertEquals(client, savedClient);
        verify(notificationServiceClient, times(1))
                .sendEmail(eq("test@example.com"),
                        eq("Confirmation Création de compte"),
                        eq("Votre création de compte a été effectué avec succès."));
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testGetAllClient() {
        Client client1 = new Client();
        Client client2 = new Client();
        List<Client> clients = Arrays.asList(client1, client2);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClient();

        assertEquals(2, result.size());
        assertTrue(result.contains(client1));
        assertTrue(result.contains(client2));
    }

    @Test
    void testDeleteClient() {
        Long id = 1L;

        doNothing().when(clientRepository).deleteById(id);

        clientService.deleteClient(id);

        verify(clientRepository, times(1)).deleteById(id);
    }
}
