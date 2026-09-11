package com.ITGate.crm.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String message;
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String login;
    private String role;
    private String token;
}

