package com.jpstechno.immo_backend.serviceImplementations;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jpstechno.immo_backend.dto.UtilisateurDtoRequest;
import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;
import com.jpstechno.immo_backend.dtoMappers.UtilisateurDtoMapper;
import com.jpstechno.immo_backend.enumerations.TrieOrderEnums;
import com.jpstechno.immo_backend.enumerations.UserRole;
import com.jpstechno.immo_backend.gestionEvenements.mesEvenements.NewUserCreatedEvent;
import com.jpstechno.immo_backend.modeles.UserTemporaireToken;
import com.jpstechno.immo_backend.modeles.Utilisateurs;
import com.jpstechno.immo_backend.repositories.TokenRepositories;
import com.jpstechno.immo_backend.repositories.UtilisateurRepositories;
import com.jpstechno.immo_backend.services.UtilisateurService;

import jakarta.transaction.Transactional;

@Service
public class UtilisateurImplementations implements UtilisateurService {

    private final UtilisateurRepositories utilisateurRepo;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final TokenRepositories userTokenRepo;
    // private final UtilisateurDtoMapper utilisateurDtoMapper;

    public UtilisateurImplementations(UtilisateurRepositories utilisateurRepo, PasswordEncoder passwordEncoder,
            ApplicationEventPublisher eventPublisher, TokenRepositories userTokenRepo) {
        this.utilisateurRepo = utilisateurRepo;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.userTokenRepo = userTokenRepo;
        // this.utilisateurDtoMapper = utilisateurDtoMapper;
    }

    @Override
    @Transactional
    public UtilisateurDtoResponse createNewUser(UtilisateurDtoRequest utilisateurData, MultipartFile photo) {

        Utilisateurs newUserData = UtilisateurDtoMapper.INSTANCE.dtoRequestToUtilisateurs(utilisateurData);
        newUserData.setRoles(Set.of(UserRole.UTILISATEUR)); // role UTILISATEUR par defaut

        // Crypter le mot de passe avant de le sauvegarder
        String hashedPassword = passwordEncoder.encode(newUserData.getPassword());
        newUserData.setPassword(hashedPassword);

        Utilisateurs createdUser = utilisateurRepo.save(newUserData);

        // ajout de la photo de l'utilisateur si elle est fournie
        if (photo != null && !photo.isEmpty()) {
            String photoUrl = savePhotoFile(createdUser.getUserID(), photo);
            createdUser.setPhotoUrl(photoUrl);
            utilisateurRepo.save(createdUser); // Mettre à jour l'utilisateur avec l'URL de la photo
        }

        // Creer un evenement pour validation email
        UtilisateurDtoResponse newUserDto = UtilisateurDtoMapper.INSTANCE.utilisateurToDtoResponse(createdUser);
        NewUserCreatedEvent newUserEvent = new NewUserCreatedEvent(newUserDto);
        eventPublisher.publishEvent(newUserEvent);

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

    @Override
    public String ajouterModifierPhotoUtilisateur(Long userId, MultipartFile photoFile) {
        try {
            savePhotoFile(userId, photoFile);
            return "photo bien ajoutee a votre profil";

        } catch (Exception e) {
            return "Echec lors de la modification de la photo";
        }

    }

    /**
     * Enregistre le fichier photo pour l'utilisateur spécifié et retourne l'URL de
     * la photo.
     * Cette méthode doit être implémentée pour gérer le stockage réel du fichier
     * photo.
     * 
     * @param userId    L'ID de l'utilisateur pour lequel la photo est ajoutée ou
     *                  modifiée.
     * @param photoFile Le fichier photo à enregistrer.
     * @return L'URL de la photo enregistrée.
     */
    private String savePhotoFile(Long userId, MultipartFile photoFile) {

        String dossierDestination = "photos/utilisateurs";

        if (photoFile == null || photoFile.isEmpty()) {
            throw new IllegalArgumentException("Le fichier photo est vide ou null.");
        }

        String extensionPhoto = photoFile.getContentType();
        List<String> extensionAutorisees = List.of("image/jpeg", "image/png");
        if (!extensionAutorisees.contains(extensionPhoto)) {
            throw new IllegalArgumentException("type de photo non pris en charge \n Utiliez les formats jpeg, png");
        }

        if (photoFile.getSize() > 2 * 1024 * 1024) {
            throw new IllegalArgumentException("la taille de la photo ne doit pas exceder 2 Mo");
        }

        Utilisateurs utilisateur = this.rechercherUtilisateurParId(userId);

        try {
            String nomFichier = String.valueOf(utilisateur.getUserID());
            Path dossier = Files.createDirectories(Path.of(dossierDestination));
            Path cheminFichier = dossier.resolve(nomFichier + "." + extensionPhoto.substring(6));
            Files.write(cheminFichier, photoFile.getBytes());
            return cheminFichier.toString(); // Retourne le chemin du fichier enregistré

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier photo: \n" + e.getMessage());
        }
    }

    /**
     * Recherche un utilisateur par son ID
     * retourne un objet de type Utilisateurs
     * Si l'utilisateur n'est pas trouvé, une exception est levée.
     * 
     * @param userId L'ID de l'utilisateur à rechercher.
     * @return L'objet Utilisateurs correspondant.
     */
    private Utilisateurs rechercherUtilisateurParId(Long userId) {
        return utilisateurRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + userId));

    }

    @Override
    @Transactional
    public String verifierEmail(Long id, String token) {
        String result = "";
        Optional<UserTemporaireToken> tokenVerification = userTokenRepo.findByIdAndToken(id, token);
        if (tokenVerification.isPresent() && tokenVerification.get().getExpireAT().isAfter(LocalDateTime.now())) {
            // si token present et non expire
            Utilisateurs utilisateur = utilisateurRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Utilisateur not found dans notre systeme"));
            utilisateur.setEmailValide(true);
            utilisateur.setActif(true);
            utilisateurRepo.save(utilisateur);

            // supprimer en meme temps le token dans la base de donnees
            userTokenRepo.deleteByIdAndToken(id, token);
            result = "validation du token reussie";

        } else {
            result = "Echec de la validation du token";
        }

        return result;
    }

}
