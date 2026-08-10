package com.yourcompany.crm.repository;

import com.yourcompany.crm.enums.TicketPriority;
import com.yourcompany.crm.enums.TicketStatus;
import com.yourcompany.crm.model.Client;
import com.yourcompany.crm.model.Ticket;
import com.yourcompany.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findByReference(String reference);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByPriority(TicketPriority priority);
    List<Client> findByClientId(Long clientId);
    List<User> findByUserId(Long userId);
}
