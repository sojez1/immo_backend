package com.jpstechno.immo_backend.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpstechno.immo_backend.modeles.UserTemporaireToken;

public interface TokenRepositories extends JpaRepository<UserTemporaireToken, Long> {

    /**
     * Permet de supprimer tous les token expire
     * 
     * @param maintenant
     */
    @Modifying
    @Query("DELETE FROM UserTemporaireToken tk WHERE tk.expireAT < :maintenant")
    void deleteExpiredTokens(@Param("maintenant") LocalDateTime maintenant);

    /**
     * Sert a retrouver un token en fonction de id de l'utilisateur et de son token
     * 
     * @param userId identifiant de l'utilisateur
     * @param token  token de verification de email
     * @return
     */
    @Query("SELECT tk FROM UserTemporaireToken tk WHERE tk.userId = :userId AND tk.token = :token")
    Optional<UserTemporaireToken> findByIdAndToken(@Param("userId") Long userId, @Param("token") String token);

    /**
     * Sert a supprimer un token connaissant id de l'utilisateur et le token
     * 
     * @param userId id de l'utilisateur
     * @param token  token de verification du mot de passe
     */
    @Modifying
    @Query("DELETE FROM UserTemporaireToken tk WHERE tk.userId = :userId AND tk.token = :token")
    void deleteByIdAndToken(@Param("userId") Long userId, @Param("token") String token);

}
