package com.ITGate.crm.service;

import com.ITGate.crm.dto.dashboard.DashboardResponseDTO;
import com.ITGate.crm.enums.TicketStatus;
import com.ITGate.crm.repository.TicketRepository;
import com.ITGate.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public DashboardResponseDTO getDashboard() {

        long totalTickets = ticketRepository.count();

        long ticketsFermes =
                ticketRepository.countByStatut(TicketStatus.FERME);

        long ticketsOuverts =
                ticketRepository.countByStatut(TicketStatus.NOUVEAU)
                        + ticketRepository.countByStatut(TicketStatus.EN_COURS)
                        + ticketRepository.countByStatut(TicketStatus.EN_ATTENTE)
                        + ticketRepository.countByStatut(TicketStatus.RESOLU);

        long totalUtilisateurs = userRepository.count();

        return new DashboardResponseDTO(
                totalTickets,
                ticketsOuverts,
                ticketsFermes,
                totalUtilisateurs
        );
    }
}