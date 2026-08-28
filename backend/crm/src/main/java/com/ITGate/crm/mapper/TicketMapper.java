package com.ITGate.crm.mapper;

import com.ITGate.crm.dto.ticket.TicketRequestDTO;
import com.ITGate.crm.dto.ticket.TicketResponseDTO;
import com.ITGate.crm.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "user", ignore = true)
    Ticket toTicket(TicketRequestDTO dto);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.raisonSociale")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(
            target = "userName",
            expression = "java(ticket.getUser().getPrenom() + \" \" + ticket.getUser().getNom())"
    )
    TicketResponseDTO toTicketResponseDTO(Ticket ticket);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateTicket(
            TicketRequestDTO dto,
            @MappingTarget Ticket ticket
    );
}
