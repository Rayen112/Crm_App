package com.ITGate.crm.mapper;

import com.ITGate.crm.dto.user.UserRequestDTO;
import com.ITGate.crm.dto.user.UserResponseDTO;
import com.ITGate.crm.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequestDTO request);

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    UserResponseDTO toUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUser(
            UserRequestDTO request,
            @MappingTarget User user
    );
}
