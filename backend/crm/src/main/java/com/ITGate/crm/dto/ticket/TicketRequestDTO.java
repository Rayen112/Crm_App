package com.ITGate.crm.dto.ticket;

import com.ITGate.crm.enums.TicketPriority;
import com.ITGate.crm.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO {

    @NotBlank(message = "L'objet est obligatoire")
    @Size(max = 200,
            message = "L'objet ne doit pas dépasser 200 caractères")
    private String objet;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "La priorité est obligatoire")
    private TicketPriority priorite;

    @NotNull(message = "Le statut est obligatoire")
    private TicketStatus statut;

    @NotNull(message = "Le client est obligatoire")
    private Long clientId;

    @NotNull(message = "L'utilisateur est obligatoire")
    private Long userId;
}
