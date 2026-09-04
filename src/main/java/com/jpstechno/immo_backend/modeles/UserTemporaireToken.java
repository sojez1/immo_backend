package com.jpstechno.immo_backend.modeles;

import java.time.LocalDateTime;

import com.jpstechno.immo_backend.enumerations.TokenCreatedFor;
import com.jpstechno.immo_backend.utilitaires.ParametresApplication;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
                @UniqueConstraint(name = "uk_token_utilisateur", columnNames = { "user_id", "createdFor" })
})
public class UserTemporaireToken {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "user_id")
        private long userId;

        @Enumerated(EnumType.STRING)
        private TokenCreatedFor createdFor;

        private String token;

        @Builder.Default
        private LocalDateTime expireAT = LocalDateTime.now()
                        .plusMinutes(ParametresApplication.DUREE_TOKEN_EMAIL_VALIDATION);

}
