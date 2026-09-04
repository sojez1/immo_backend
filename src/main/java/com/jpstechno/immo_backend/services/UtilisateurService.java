package com.jpstechno.immo_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.jpstechno.immo_backend.dto.UtilisateurDtoRequest;
import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;
import com.jpstechno.immo_backend.enumerations.TrieOrderEnums;

public interface UtilisateurService {

    UtilisateurDtoResponse createNewUser(UtilisateurDtoRequest utilisateurData, MultipartFile photo);

    String ajouterModifierPhotoUtilisateur(Long userId, MultipartFile photoFile);

    Page<UtilisateurDtoResponse> getListeUtilisateurs(int numPage, int pageSize, String champAtrier,
            TrieOrderEnums ordreTrie);

    String verifierEmail(Long id, String token);
}
