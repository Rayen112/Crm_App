package com.ITGate.crm.dto.ticket;

import com.ITGate.crm.enums.TicketPriority;
import com.ITGate.crm.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    @NotBlank(message = "L'objet est obligatoire")
    private String objet;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    private TicketPriority priorite;

    private TicketStatus statut;

    private Long clientId;

    private Long userId;
}
