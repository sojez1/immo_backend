/**
 * permet aux utilisateurs de publier des recherches de locations sur la plateforme.
 */

package com.jpstechno.immo_backend.modeles;

import java.math.BigDecimal;

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
public class DemandeLocations {

    private Long id;

    private BigDecimal budgetMensuel;

    private String votreMessage;
}
