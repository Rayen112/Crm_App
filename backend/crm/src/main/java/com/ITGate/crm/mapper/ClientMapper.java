package com.ITGate.crm.mapper;

import com.ITGate.crm.dto.client.ClientRequestDTO;
import com.ITGate.crm.dto.client.ClientResponseDTO;
import com.ITGate.crm.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    Client toClient(ClientRequestDTO dto);

    ClientResponseDTO toClientResponseDTO(Client client);

    @Mapping(target = "id", ignore = true)
    void updateClient(
            ClientRequestDTO dto,
            @MappingTarget Client client
    );
}