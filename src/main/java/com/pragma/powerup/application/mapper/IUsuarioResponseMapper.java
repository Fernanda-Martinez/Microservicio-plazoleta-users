package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.CrearUsuarioResponseDto;
import com.pragma.powerup.domain.model.Usuarios;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IUsuarioResponseMapper {
    CrearUsuarioResponseDto toResponse(Usuarios usuarios);
}
