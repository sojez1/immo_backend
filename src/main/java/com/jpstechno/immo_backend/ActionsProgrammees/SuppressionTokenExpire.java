package com.jpstechno.immo_backend.ActionsProgrammees;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jpstechno.immo_backend.repositories.TokenRepositories;
import com.jpstechno.immo_backend.utilitaires.ParametresApplication;

@Service
public class SuppressionTokenExpire {

    private final TokenRepositories tokenRepo;

    public SuppressionTokenExpire(TokenRepositories tokenRepo) {
        this.tokenRepo = tokenRepo;

    }

    /**
     * Permet de supprimer de la BDD, tous les tokens expirees
     * S'execute toutes les 15 mn.
     */
    @Scheduled(fixedRate = ParametresApplication.DELAI_SUPPRESSION_TOKEN)
    public void deleteExpiredToken() {
        LocalDateTime instantPresent = LocalDateTime.now();
        tokenRepo.deleteExpiredTokens(instantPresent);
    }

}
