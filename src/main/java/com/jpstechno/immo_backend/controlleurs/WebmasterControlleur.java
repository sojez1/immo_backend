package com.jpstechno.immo_backend.controlleurs;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;
import com.jpstechno.immo_backend.modeles.ContratDeLocations;
import com.jpstechno.immo_backend.services.UtilisateurService;

@RestController
@RequestMapping("/webmaster")
@CrossOrigin
public class WebmasterControlleur {

    private final UtilisateurService userService;

    public WebmasterControlleur(UtilisateurService userService) {
        this.userService = userService;
    }

    public List<UtilisateurDtoResponse> AllAppUser() {
        return null;

    }

    public ContratDeLocations getContratById(Long numeroContrat) {
        return null;
    }

}
