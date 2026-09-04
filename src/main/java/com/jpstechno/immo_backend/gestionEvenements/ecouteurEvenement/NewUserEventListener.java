package com.jpstechno.immo_backend.gestionEvenements.ecouteurEvenement;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;
import com.jpstechno.immo_backend.enumerations.TokenCreatedFor;
import com.jpstechno.immo_backend.gestionEvenements.mesEvenements.NewUserCreatedEvent;
import com.jpstechno.immo_backend.modeles.UserTemporaireToken;
import com.jpstechno.immo_backend.repositories.TokenRepositories;
import com.jpstechno.immo_backend.utilitaires.Coursiers;
import com.jpstechno.immo_backend.utilitaires.ParametresApplication;

@Component
public class NewUserEventListener {

    private final Coursiers coursier;
    private final TokenRepositories tokenRepo;
    private final SpringTemplateEngine templateEngine;

    public NewUserEventListener(Coursiers coursier, SpringTemplateEngine templateEngine, TokenRepositories tokenRepo) {
        this.coursier = coursier;
        this.templateEngine = templateEngine;
        this.tokenRepo = tokenRepo;
    }

    String controlleurPathForEmailVerification = "/utilisateurs/profil/email/verifier";

    @EventListener
    @Async
    public void sendConfirmationEmail(NewUserCreatedEvent newUserEvent) {

        // 1- Récupérer les informations de l'utilisateur à partir de l'événement
        UtilisateurDtoResponse createdUser = (UtilisateurDtoResponse) newUserEvent.getSource();

        // 2- Générer un token securise de confirmation unique pour l'utilisateur
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenByte = new byte[32];
        secureRandom.nextBytes(tokenByte);
        String emailConfirmationToken = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenByte);

        // 3- Enregistrer le token en base de donnees
        UserTemporaireToken userToken = UserTemporaireToken.builder()
                .createdFor(TokenCreatedFor.EMAIL_VALIDATION)
                .token(emailConfirmationToken)
                .userId(createdUser.userID())
                .build();
        tokenRepo.save(userToken);

        // 4- Construire le lien de confirmation en incluant le token
        String confirmationUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(controlleurPathForEmailVerification)
                .queryParam("id", createdUser.userID())
                .queryParam("token", emailConfirmationToken)
                .toUriString();

        // 5- Envoyer mail contenant le lien de confirmation
        String objetMessage = "Validation de votre courriel sur JPS-Immo";
        String destinataire = createdUser.email();
        final Context thymleafContext = new Context();
        thymleafContext.setVariable("prenom", createdUser.prenoms() + " " + createdUser.nom());
        thymleafContext.setVariable("verificationUrl", confirmationUrl);
        thymleafContext.setVariable("delai", String.valueOf(ParametresApplication.DUREE_TOKEN_EMAIL_VALIDATION));

        String corpsMessage = templateEngine.process("emails/InscriptionEmailValidation.html", thymleafContext);
        coursier.envoyerMail(objetMessage, corpsMessage, destinataire);

    }

}
