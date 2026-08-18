package com.yourcompany.crm.service;

import com.yourcompany.crm.model.Client;
import com.yourcompany.crm.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client client) {
        Client existingClient = getClientById(id);

        existingClient.setRaisonSociale(client.getRaisonSociale());
        existingClient.setNom(client.getNom());
        existingClient.setTelephone(client.getTelephone());
        existingClient.setEmail(client.getEmail());

        return clientRepository.save(existingClient);
    }

    public void deleteClient(Long id) {
        Client client = getClientById(id);
        clientRepository.delete(client);
    }
}
