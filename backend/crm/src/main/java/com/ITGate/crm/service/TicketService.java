package com.ITGate.crm.service;

import com.ITGate.crm.dto.ticket.TicketRequestDTO;
import com.ITGate.crm.dto.ticket.TicketResponseDTO;
import com.ITGate.crm.enums.TicketPriority;
import com.ITGate.crm.enums.TicketStatus;
import com.ITGate.crm.mapper.TicketMapper;
import com.ITGate.crm.model.Client;
import com.ITGate.crm.model.Ticket;
import com.ITGate.crm.model.User;
import com.ITGate.crm.repository.ClientRepository;
import com.ITGate.crm.repository.TicketRepository;
import com.ITGate.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;


    // GET ALL
    public List<TicketResponseDTO> getAllTickets() {

        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList();
    }


    // GET BY ID
    public TicketResponseDTO getTicketById(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Ticket not found with id: " + id
                        )
                );

        return ticketMapper.toTicketResponseDTO(ticket);
    }


    // CREATE
    public TicketResponseDTO createTicket(
            TicketRequestDTO request
    ) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Client not found with id: " + request.getClientId()
                        )
                );

        Long userId = request.getUserId();
        if (userId == null || userId <= 0) {
            org.springframework.security.core.Authentication auth =
                    org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
                User currentUser = userRepository.findByLogin(auth.getName()).orElse(null);
                if (currentUser != null) {
                    userId = currentUser.getId();
                }
            }
        }

        if (userId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "L'utilisateur assigné au ticket est obligatoire"
            );
        }

        final Long targetUserId = userId;
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found with id: " + targetUserId
                        )
                );

        Ticket ticket = ticketMapper.toTicket(request);

        ticket.setClient(client);
        ticket.setUser(user);
        if (ticket.getStatut() == null) {
            ticket.setStatut(TicketStatus.NOUVEAU);
        }
        ticket.setReference("TEMP-" + System.currentTimeMillis());
        Ticket savedTicket = ticketRepository.save(ticket);
        savedTicket.setReference("TKT-" + String.format("%04d", savedTicket.getId()));

        savedTicket = ticketRepository.save(savedTicket);
        return ticketMapper.toTicketResponseDTO(savedTicket);
    }


    // UPDATE
    public TicketResponseDTO updateTicket(
            Long id,
            TicketRequestDTO request
    ) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Ticket not found with id: " + id
                        )
                );

        ticketMapper.updateTicket(request, ticket);

        if (request.getClientId() != null) {

            Client client = clientRepository
                    .findById(request.getClientId())
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Client not found "
                            )
                    );

            ticket.setClient(client);
        }

        if (request.getUserId() != null) {

            User user = userRepository
                    .findById(request.getUserId())
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "User not found "
                            )
                    );

            ticket.setUser(user);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toTicketResponseDTO(updatedTicket);
    }


    // DELETE
    public void deleteTicket(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Ticket not found with id: " + id
                        )
                );

        ticketRepository.delete(ticket);
    }


    // FILTER BY STATUS
    public List<TicketResponseDTO> getTicketsByStatus(
            TicketStatus status
    ) {

        return ticketRepository.findByStatut(status)
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList();
    }


    // FILTER BY PRIORITY
    public List<TicketResponseDTO> getTicketsByPriority(
            TicketPriority priority
    ) {

        return ticketRepository.findByPriorite(priority)
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList();
    }


    // FILTER BY CLIENT
    public List<TicketResponseDTO> getTicketsByClient(
            Long clientId
    ) {

        return ticketRepository.findByClientId(clientId)
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList();
    }


    // FILTER BY USER
    public List<TicketResponseDTO> getTicketsByUser(
            Long userId
    ) {

        return ticketRepository.findByUserId(userId)
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList();
    }

    public List<TicketResponseDTO> searchTickets(String keyword) {
        return ticketRepository
                .findByObjetContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        keyword,
                        keyword
                )
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList();
    }

    public List<TicketResponseDTO> filterByDate(
            LocalDate dateDebut,
            LocalDate dateFin) {

        LocalDateTime startDateTime = dateDebut.atStartOfDay();
        LocalDateTime endDateTime = dateFin.atTime(LocalTime.MAX);

        return ticketRepository
                .findByDateCreationBetween(startDateTime, endDateTime)
                .stream()
                .map(ticketMapper::toTicketResponseDTO)
                .toList();
    }

}
