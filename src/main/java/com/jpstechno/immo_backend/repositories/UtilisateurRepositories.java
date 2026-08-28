package com.jpstechno.immo_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jpstechno.immo_backend.modeles.Utilisateurs;

public interface UtilisateurRepositories extends JpaRepository<Utilisateurs, Long> {

    Page<Utilisateurs> findAll(Pageable pageable);

}
