package com.jpstechno.immo_backend.controlleurs;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jpstechno.immo_backend.dto.UtilisateurDtoRequest;
import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;
import com.jpstechno.immo_backend.enumerations.TrieOrderEnums;
import com.jpstechno.immo_backend.securites.MyPrincipal;
import com.jpstechno.immo_backend.services.UtilisateurService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/utilisateurs")
public class AuthenticationControlleur {

    private UtilisateurService userService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationControlleur(UtilisateurService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/profil/new-user")
    public ResponseEntity<UtilisateurDtoResponse> createNewuser(@Valid @RequestPart UtilisateurDtoRequest userdto,
            @RequestPart MultipartFile photo) {
        UtilisateurDtoResponse userResponse = userService.createNewUser(userdto, photo);
        return new ResponseEntity<UtilisateurDtoResponse>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/authentication/login")
    public ResponseCookie login(
            @Valid @RequestBody UtilisateurDtoRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        if (auth.isAuthenticated()) {
            MyPrincipal userPrincipal = (MyPrincipal) auth.getPrincipal();
            String access_token = "abvchfjf";
            ResponseCookie cookie = ResponseCookie.from("access_token", access_token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(24 * 60 * 60) // 1 day
                    .sameSite("Strict")
                    .build();
            return cookie;

        } else {
            throw new BadCredentialsException("Invalid username or password");
        }

    }

    @PostMapping("/authentication/logout")
    public ResponseEntity<?> logout() {
        return null;
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword() {
        return null;
    }

    @GetMapping("/profil/email/verifier")
    public ResponseEntity<?> verifierEmail(@RequestParam Long id, @RequestParam String token) {
        return userService.verifierEmail(id, token);
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
