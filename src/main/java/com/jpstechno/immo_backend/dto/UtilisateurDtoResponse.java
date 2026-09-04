package com.jpstechno.immo_backend.dto;

import java.time.LocalDate;

public record UtilisateurDtoResponse(
                Long userID,

                String nom,

                String prenoms,

                String pseudo,

                String email,

                boolean actif,

                boolean emailValide,

                LocalDate dateInscription,

                String photoUrl) {

}
