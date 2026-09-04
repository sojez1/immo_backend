/**
 * Permet aux proprietaires de publier leurs offres de locations sur la plateforme.
 * Cette classe contient des informations sur le type d'appartement, le nombre de chambres, le nombre de salles de bain, la presence d'une cuisine, la superficie et les commentaires.
 */

package com.jpstechno.immo_backend.modeles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class OffreLocations {

    @Id
    private Long id;

    @Builder.Default
    private LocalDate datePublication = LocalDate.now(); // date de publication de l'offre de location

    private List<String> photoAppartement; // liste des liens menants aux photos de l'appartement

    @ManyToOne
    private Appartements appartement; // appartement proposé à la location

    private BigDecimal prixMensuel;

    private String commentaires;

    private boolean disponible; // indique si l'appartement est encore disponible à la location ou non

}
