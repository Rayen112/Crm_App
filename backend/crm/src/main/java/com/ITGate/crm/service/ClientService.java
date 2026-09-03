package com.ITGate.crm.service;

import com.ITGate.crm.dto.client.ClientRequestDTO;
import com.ITGate.crm.dto.client.ClientResponseDTO;
import com.ITGate.crm.mapper.ClientMapper;
import com.ITGate.crm.model.Client;
import com.ITGate.crm.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    // GET ALL
    public List<ClientResponseDTO> getAllClients() {

        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toClientResponseDTO)
                .toList();
    }


    // GET BY ID
    public ClientResponseDTO getClientById(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Client not found with id: " + id
                        )
                );;

        return clientMapper.toClientResponseDTO(client);
    }


    // CREATE
    public ClientResponseDTO createClient(ClientRequestDTO request) {

        Client client = clientMapper.toClient(request);

        Client savedClient = clientRepository.save(client);

        return clientMapper.toClientResponseDTO(savedClient);
    }


    // UPDATE
    public ClientResponseDTO updateClient(
            Long id,
            ClientRequestDTO request
    ) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Client not found with id: " + id
                        )
                );

        clientMapper.updateClient(request, client);

        Client updatedClient = clientRepository.save(client);

        return clientMapper.toClientResponseDTO(updatedClient);
    }


    // DELETE
    public void deleteClient(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Client not found with id: " + id
                        )
                );

        clientRepository.delete(client);
    }
}
