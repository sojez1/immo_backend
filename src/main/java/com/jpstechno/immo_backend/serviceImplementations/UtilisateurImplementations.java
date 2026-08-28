package com.jpstechno.immo_backend.serviceImplementations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jpstechno.immo_backend.dto.UtilisateurDtoRequest;
import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;
import com.jpstechno.immo_backend.dtoMappers.UtilisateurDtoMapper;
import com.jpstechno.immo_backend.enumerations.TrieOrderEnums;
import com.jpstechno.immo_backend.modeles.Utilisateurs;
import com.jpstechno.immo_backend.repositories.UtilisateurRepositories;
import com.jpstechno.immo_backend.services.UtilisateurService;

@Service
public class UtilisateurImplementations implements UtilisateurService {

    private final UtilisateurRepositories utilisateurRepo;
    // private final UtilisateurDtoMapper utilisateurDtoMapper;

    public UtilisateurImplementations(UtilisateurRepositories utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
        // this.utilisateurDtoMapper = utilisateurDtoMapper;
    }

    @Override
    public UtilisateurDtoResponse createNewUser(UtilisateurDtoRequest utilisateurData) {

        Utilisateurs newUserData = UtilisateurDtoMapper.INSTANCE.dtoRequestToUtilisateurs(utilisateurData);

        Utilisateurs createdUser = utilisateurRepo.save(newUserData);

        return UtilisateurDtoMapper.INSTANCE.utilisateurToDtoResponse(createdUser);
    }

    @Override
    public Page<UtilisateurDtoResponse> getListeUtilisateurs(int numPage, int pageSize, String champAtrier,
            TrieOrderEnums ordreTrie) {
        Sort trie = Sort.by(Sort.Direction.ASC, champAtrier);
        PageRequest pageable = PageRequest.of(numPage, pageSize, trie);
        Page<Utilisateurs> result = utilisateurRepo.findAll(pageable);
        return result.map(utilsateur -> UtilisateurDtoMapper.INSTANCE.utilisateurToDtoResponse(utilsateur));
    }

}
