package com.ITGate.crm.controller;

import com.ITGate.crm.dto.client.ClientRequestDTO;
import com.ITGate.crm.dto.client.ClientResponseDTO;
import com.ITGate.crm.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;


    // GET /api/clients
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {

        return ResponseEntity.ok(
                clientService.getAllClients()
        );
    }


    // GET /api/clients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clientService.getClientById(id)
        );
    }


    // POST /api/clients
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(
            @RequestBody ClientRequestDTO request) {

        ClientResponseDTO createdClient =
                clientService.createClient(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdClient);
    }


    // PUT /api/clients/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable Long id,
            @RequestBody ClientRequestDTO request) {

        return ResponseEntity.ok(
                clientService.updateClient(id, request)
        );
    }


    // DELETE /api/clients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable Long id) {

        clientService.deleteClient(id);

        return ResponseEntity.noContent().build();
    }
}