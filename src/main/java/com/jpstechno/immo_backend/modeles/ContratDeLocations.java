/**
 * Perment de représenter un contrat de location entre un propriétaire et un locataire pour un appartement donné.
 * 
 */

package com.jpstechno.immo_backend.modeles;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ContratDeLocations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appartement_id")
    private Appartements appartement; // appartement concerné par le contrat de location

    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Proprietaires proprietaire; // propriétaire actuelle de l'appartement

    private LocalDate dateDebut; // date de début du contrat de location

    private LocalDate dateFin; // date de fin du contrat de location

    private BigDecimal montantLoyer; // montant du loyer mensuel

    private BigDecimal montantCaution; // montant de la caution

    private LocalDate dateSignature; // date de signature du contrat de location
}
