package com.jpstechno.immo_backend.dto;

public record UtilisateurDtoResponse(
        Long id,

        String nom,

        String prenoms,

        String pseudo,

        String email,

        boolean actif,

        boolean emailValide) {

}
