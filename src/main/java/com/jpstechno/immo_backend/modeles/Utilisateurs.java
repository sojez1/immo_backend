package com.jpstechno.immo_backend.modeles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Utilisateurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vous devez saisir votre nom")
    private String nom;

    @NotBlank(message = "Vous devez indiquer votre prenom")
    private String prenoms;

    private String pseudo;

    @Email
    private String email;

    private String password;

    @Builder.Default
    private boolean actif = true;

    @Builder.Default
    private boolean emailValide = false;

}