package com.jpstechno.immo_backend.dtoMappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.jpstechno.immo_backend.dto.UtilisateurDtoRequest;
import com.jpstechno.immo_backend.dto.UtilisateurDtoResponse;
import com.jpstechno.immo_backend.modeles.Utilisateurs;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UtilisateurDtoMapper {

    UtilisateurDtoMapper INSTANCE = Mappers.getMapper(UtilisateurDtoMapper.class);

    Utilisateurs dtoRequestToUtilisateurs(UtilisateurDtoRequest utilisateurDtoRequest);

    UtilisateurDtoResponse utilisateurToDtoResponse(Utilisateurs userData);
}
