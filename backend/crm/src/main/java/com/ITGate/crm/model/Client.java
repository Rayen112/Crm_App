package com.ITGate.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "raison_sociale", length = 150)
    private String raisonSociale;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 30)
    private String telephone;

    @Column(length = 150)
    private String email;
}
