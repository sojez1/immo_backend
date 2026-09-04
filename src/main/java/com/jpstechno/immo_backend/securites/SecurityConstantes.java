package com.jpstechno.immo_backend.securites;

import java.util.List;

public class SecurityConstantes {

        public static final List<String> METHODE_AUTORISEE = List.of(
                        "PUT",
                        "POST",
                        "GET",
                        "DELETE",
                        "OPTIONS");

        public static final List<String> ORIGINE_AUTORISEE = List.of(
                        "http://localhost:8181",
                        "https://immo.jpstechno.com");

        public static final String[] PUBLIC_ENDPOINTS = {
                        "/utilisateurs/login",
                        "/utilisateurs/profil/new-user",
                        "/utilisateurs/profil/email/verifier",
                        "/error"

        };

        public static final List<String> ENTETES_AUTORISES = List.of(
                        "Authorization",
                        "Content-Type");

}
