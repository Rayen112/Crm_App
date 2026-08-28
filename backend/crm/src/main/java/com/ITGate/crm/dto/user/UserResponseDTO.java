package com.ITGate.crm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private String login;

    private String telephone;

    private Long roleId;

    private String roleName;
}
