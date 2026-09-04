/**
 * permet de recuperer les informations de l'utilisateur authentifié
 */

package com.jpstechno.immo_backend.securites;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jpstechno.immo_backend.modeles.Utilisateurs;

public class MyPrincipal implements UserDetails {

    private final Utilisateurs utilisateur;

    public MyPrincipal(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return utilisateur.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public @Nullable String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return true;

    }

}
