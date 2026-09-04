/**
 * Cette classe permet de modeliser les appartements (ou unites) d'un imeuble.
 * Elle contient des informations sur le type d'appartement, le nombre de chambres, le nombre de salles de bain, la presence d'une cuisine, la superficie et les commentaires.
 */

package com.jpstechno.immo_backend.modeles;

import java.util.List;

import com.jpstechno.immo_backend.enumerations.TypeAppartement;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Appartements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeAppartement typeAppartement;

    private int nombreChambres;

    private int nombreSallesDeBain;

    private boolean cuisine;

    private double superficie;

    private String commentaires;

    @ManyToOne
    @JoinColumn(name = "immeuble_id")
    private Immeubles immeuble; // Association avec l'entité Immeubles

    @OneToMany(mappedBy = "appartement")
    private List<ContratDeLocations> contratsDeLocation; // Liste des contrats de location associés à l'appartement

    @OneToMany(mappedBy = "appartement")
    private List<OffreLocations> listeOffresLocation; // Liste des offres de location associées à l'appartement
}
