package com.ITGate.crm.repository;

import com.ITGate.crm.enums.TicketPriority;
import com.ITGate.crm.enums.TicketStatus;
import com.ITGate.crm.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    // Recherche par objet ou description
    List<Ticket> findByObjetContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String objet,
            String description
    );
    Optional<Ticket> findByReference(String reference);
    List<Ticket> findByStatut(TicketStatus statut);
    List<Ticket> findByPriorite(TicketPriority priorite);
    List<Ticket> findByClientId(Long clientId);
    List<Ticket> findByUserId(Long userId);
    long countByStatut(TicketStatus statut);
    // Filtre entre deux dates
    List<Ticket> findByDateCreationBetween(
            LocalDateTime dateDebut,
            LocalDateTime dateFin
    );
}
