package com.jpstechno.immo_backend.dto;

//import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public record UtilisateurDtoRequest(
        @NotBlank(message = "Vous devez saisir votre nom") String nom,

        @NotBlank(message = "Vous devez indiquer votre prenom") String prenoms,

        String username,

        @Email String email,

        String password

) {

}
