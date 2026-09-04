package com.jpstechno.immo_backend.modeles;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Immeubles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appelationCourante; // Nom de l'immeuble (exemple immeuble Kouglenou)

    private String adresse;

    @OneToMany(mappedBy = "immeuble", fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Appartements> appartements = new HashSet<>(); // Liste des appartements associés à l'immeuble

}
