/**
 * enumeration pour contenir les roles des utilisateurs
 * Differe des classes proprietaires, locataires, ...
 * Ces enums sont utilisés pour la gestion des droits d'accès et des autorisations dans l'application.
 * tandis que les classes propriétaires, locataires, etc. représentent des entités spécifiques avec leurs propres attributs et comportements.
 * 
 */

package com.jpstechno.immo_backend.enumerations;

public enum UserRole {
    LOCATAIRE,
    PROPRIETAIRE,
    UTILISATEUR, // Par defaut suite inscription. N'est ni locataire, ni proprietaire
    WEBMASTER,
    ADMINISTRATEUR
}
