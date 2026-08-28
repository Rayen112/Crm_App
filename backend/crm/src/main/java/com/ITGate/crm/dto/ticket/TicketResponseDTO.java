package com.ITGate.crm.dto.ticket;

import com.ITGate.crm.enums.TicketPriority;
import com.ITGate.crm.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private Long id;

    private String reference;

    private String objet;

    private String description;

    private TicketPriority priorite;

    private TicketStatus statut;

    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;

    private Long clientId;

    private String clientName;

    private Long userId;

    private String userName;
}
