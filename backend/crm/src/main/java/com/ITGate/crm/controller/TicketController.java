package com.ITGate.crm.controller;

import com.ITGate.crm.dto.ticket.TicketRequestDTO;
import com.ITGate.crm.dto.ticket.TicketResponseDTO;
import com.ITGate.crm.enums.TicketPriority;
import com.ITGate.crm.enums.TicketStatus;
import com.ITGate.crm.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;


    // =========================
    // GET ALL TICKETS
    // =========================

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets() {

        return ResponseEntity.ok(
                ticketService.getAllTickets()
        );
    }


    // =========================
    // GET TICKET BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ticketService.getTicketById(id)
        );
    }


    // =========================
    // CREATE TICKET
    // =========================

    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(
            @Valid @RequestBody TicketRequestDTO request) {

        TicketResponseDTO createdTicket =
                ticketService.createTicket(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTicket);
    }


    // =========================
    // UPDATE TICKET
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(
            @PathVariable Long id,
            @RequestBody TicketRequestDTO request) {

        return ResponseEntity.ok(
                ticketService.updateTicket(id, request)
        );
    }


    // =========================
    // DELETE TICKET
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long id) {

        ticketService.deleteTicket(id);

        return ResponseEntity
                .noContent()
                .build();
    }


    // =========================
    // FILTER BY STATUS
    // =========================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByStatus(
            @PathVariable TicketStatus status) {

        return ResponseEntity.ok(
                ticketService.getTicketsByStatus(status)
        );
    }


    // =========================
    // FILTER BY PRIORITY
    // =========================

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByPriority(
            @PathVariable TicketPriority priority) {

        return ResponseEntity.ok(
                ticketService.getTicketsByPriority(priority)
        );
    }


    // =========================
    // GET TICKETS BY CLIENT
    // =========================

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByClient(
            @PathVariable Long clientId) {

        return ResponseEntity.ok(
                ticketService.getTicketsByClient(clientId)
        );
    }


    // =========================
    // GET TICKETS BY USER
    // =========================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                ticketService.getTicketsByUser(userId)
        );
    }
}