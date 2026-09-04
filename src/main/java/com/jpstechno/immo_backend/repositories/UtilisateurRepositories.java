package com.jpstechno.immo_backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpstechno.immo_backend.modeles.Utilisateurs;

public interface UtilisateurRepositories extends JpaRepository<Utilisateurs, Long> {

    Page<Utilisateurs> findAll(Pageable pageable);

    @Query("SELECT u FROM Utilisateurs u WHERE LOWER(u.email) = LOWER(:usernameOrEmail) OR LOWER(u.username) = LOWER(:usernameOrEmail)")
    Optional<Utilisateurs> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

}
