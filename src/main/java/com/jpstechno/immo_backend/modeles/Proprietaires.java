package com.jpstechno.immo_backend.modeles;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
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
public class Proprietaires {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private Utilisateurs utilisateur;

    @OneToMany(mappedBy = "proprietaire")
    private List<ContratDeLocations> ListeContratsLocations; // Liste des contrats de location associés au
                                                             // propriétaire

}
