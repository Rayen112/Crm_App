package com.ITGate.crm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String nom;

    private String prenom;

    private String email;

    private String login;

    private String password;

    private String telephone;

    private Long roleId;
}
