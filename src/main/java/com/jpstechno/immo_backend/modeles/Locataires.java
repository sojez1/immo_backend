package com.jpstechno.immo_backend.modeles;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Locataires {

    @Id
    private Long id;

    private String profession;
    private String PaysDeCitoyennete;
    private String situationMatrimoniale;

    @OneToOne
    @JoinColumn(name = "userID")
    private Utilisateurs utilisateur;

}
