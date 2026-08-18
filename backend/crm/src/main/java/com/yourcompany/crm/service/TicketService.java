package com.yourcompany.crm.service;

import com.yourcompany.crm.enums.TicketPriority;
import com.yourcompany.crm.enums.TicketStatus;
import com.yourcompany.crm.model.Ticket;
import com.yourcompany.crm.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id){
        return ticketRepository.findById(id).orElseThrow(()-> new RuntimeException("Ticket not found"));
    }

    public Ticket createTicket(Ticket ticket){
        if(ticket.getStatut()==null){
            ticket.setStatut(TicketStatus.NOUVEAU);
        }
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long id, Ticket ticket) {

        Ticket existingTicket = getTicketById(id);

        existingTicket.setObjet(ticket.getObjet());
        existingTicket.setDescription(ticket.getDescription());
        existingTicket.setPriorite(ticket.getPriorite());
        existingTicket.setStatut(ticket.getStatut());
        existingTicket.setClient(ticket.getClient());
        existingTicket.setUser(ticket.getUser());

        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(Long id){
        Ticket ticket = getTicketById(id);
        ticketRepository.delete(ticket);
    }

    public List<Ticket> getTicketsByStatut(TicketStatus statut){
        return ticketRepository.findByStatut(statut);
    }

    public List<Ticket> getTicketsByPriorite(TicketPriority priorite){
        return ticketRepository.findByPriorite(priorite);
    }

    public List<Ticket> getTicketsByClient(Long id){
        return ticketRepository.findByClientId(id);
    }

    public List<Ticket> getTicketsByUser(Long id){
        return ticketRepository.findByUserId(id);
    }
}
