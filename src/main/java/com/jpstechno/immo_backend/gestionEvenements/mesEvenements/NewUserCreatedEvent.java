package com.jpstechno.immo_backend.gestionEvenements.mesEvenements;

import org.springframework.context.ApplicationEvent;

import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;

public class NewUserCreatedEvent extends ApplicationEvent {

    public NewUserCreatedEvent(UtilisateurDtoResponse createdUser) {
        super(createdUser);
    }

}
