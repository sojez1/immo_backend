package com.jpstechno.immo_backend.controlleurs;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpstechno.immo_backend.dto.UtilisateurDtoRequest;
import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;
import com.jpstechno.immo_backend.enumerations.TrieOrderEnums;
import com.jpstechno.immo_backend.services.UtilisateurService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin
public class AuthenticationControlleur {

    private UtilisateurService userService;

    public AuthenticationControlleur(UtilisateurService userService) {
        this.userService = userService;
    }

    @PostMapping("/profil/new-user")
    public ResponseEntity<UtilisateurDtoResponse> createNewuser(@Valid @RequestBody UtilisateurDtoRequest userdto) {
        UtilisateurDtoResponse userResponse = userService.createNewUser(userdto);
        return new ResponseEntity<UtilisateurDtoResponse>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/authentication/login")
    public ResponseEntity<?> login() {
        return null;
    }

    @PostMapping("/authentication/logout")
    public ResponseEntity<?> logout() {
        return null;
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword() {
        return null;
    }

    @PostMapping("/profil/email/verifier")
    public ResponseEntity<?> verifierEmail() {
        return null;
    }

    @GetMapping("/profil/:id")
    public UtilisateurDtoResponse getUtilisateurById() {
        return null;
    }

    @GetMapping("/profil/all-users")
    public ResponseEntity<Page<UtilisateurDtoResponse>> getUtilisateurs(
            @RequestParam(defaultValue = "0") int numPage,
            @RequestParam(defaultValue = "2") int pageSize,
            @RequestParam(required = false, defaultValue = "nom") String champATrier,
            @RequestParam(defaultValue = "asc") TrieOrderEnums ordreTrie) {

        Page<UtilisateurDtoResponse> result = userService.getListeUtilisateurs(numPage, pageSize, champATrier,
                ordreTrie);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test")
    public String firstControlleurTest() {
        return "test reussi";
    }

}
