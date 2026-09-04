package com.jpstechno.immo_backend.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpstechno.immo_backend.modeles.UserTemporaireToken;

public interface TokenRepositories extends JpaRepository<UserTemporaireToken, Long> {

    @Modifying
    @Query("DELETE FROM UserTemporaireToken tk WHERE tk.expireAT < :maintenant")
    void deleteExpiredTokens(@Param("maintenant") LocalDateTime maintenant);

}
