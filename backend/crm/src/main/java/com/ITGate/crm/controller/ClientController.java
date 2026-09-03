package com.ITGate.crm.controller;

import com.ITGate.crm.dto.client.ClientRequestDTO;
import com.ITGate.crm.dto.client.ClientResponseDTO;
import com.ITGate.crm.service.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ClientController {

    private final ClientService clientService;


    // GET /api/clients
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT_CRM')")
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {

        return ResponseEntity.ok(
                clientService.getAllClients()
        );
    }


    // GET /api/clients/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT_CRM')")
    public ResponseEntity<ClientResponseDTO> getClientById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clientService.getClientById(id)
        );
    }


    // POST /api/clients
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT_CRM')")
    public ResponseEntity<ClientResponseDTO> createClient(
            @Valid @RequestBody ClientRequestDTO request) {

        ClientResponseDTO createdClient =
                clientService.createClient(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdClient);
    }


    // PUT /api/clients/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT_CRM')")
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequestDTO request) {

        return ResponseEntity.ok(
                clientService.updateClient(id, request)
        );
    }


    // DELETE /api/clients/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClient(
            @PathVariable Long id) {

        clientService.deleteClient(id);

        return ResponseEntity.noContent().build();
    }
}