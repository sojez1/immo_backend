package com.jpstechno.immo_backend.securites;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jpstechno.immo_backend.modeles.Utilisateurs;
import com.jpstechno.immo_backend.repositories.UtilisateurRepositories;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UtilisateurRepositories utilisateurRepository;

    public MyUserDetailService(UtilisateurRepositories utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Utilisateurs utilisateur = utilisateurRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur non trouvé avec le nom d'utilisateur ou l'email: " + usernameOrEmail));

        return new MyPrincipal(utilisateur);

    }

}
