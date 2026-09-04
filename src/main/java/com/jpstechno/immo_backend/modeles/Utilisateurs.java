package com.jpstechno.immo_backend.modeles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.jpstechno.immo_backend.enumerations.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
    private Long userID;

    @NotBlank(message = "Vous devez saisir votre nom")
    private String nom;

    @NotBlank(message = "Vous devez indiquer votre prenom")
    private String prenoms;

    private String photoUrl; // url vers la photo de l'utilisateur

    private String username;

    @Email
    @NaturalId(mutable = true)
    @Column(unique = true)
    private String email;

    @Builder.Default
    @JsonFormat(pattern = "dd-MM-aaaa", shape = Shape.STRING)
    @Column(updatable = false)
    private LocalDate dateInscription = LocalDate.now();

    private String password;

    @Builder.Default
    private boolean actif = true;

    @Builder.Default
    private boolean emailValide = false;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Set<UserRole> roles = new HashSet<>();

    @OneToOne(mappedBy = "utilisateur", optional = true, fetch = FetchType.EAGER)
    private Proprietaires proprietaire;

    @OneToOne(mappedBy = "utilisateur", optional = true, fetch = FetchType.EAGER)
    private Locataires locataire;

}