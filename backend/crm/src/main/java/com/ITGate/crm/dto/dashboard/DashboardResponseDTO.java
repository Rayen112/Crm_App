package com.ITGate.crm.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {

    private long totalTickets;
    private long ticketsOuverts;
    private long ticketsFermes;
    private long totalUtilisateurs;
}