package com.ITGate.crm.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

    @NotBlank(message = "La raison sociale est obligatoire")
    @Size(max = 150,
            message = "La raison sociale ne doit pas dépasser 150 caractères")
    private String raisonSociale;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100,
            message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;



    @NotBlank(message = "Le téléphone est obligatoire")
    @Size(max = 30,
            message = "Le téléphone ne doit pas dépasser 30 caractères")
    private String telephone;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 150,
            message = "L'email ne doit pas dépasser 150 caractères")
    private String email;
}
