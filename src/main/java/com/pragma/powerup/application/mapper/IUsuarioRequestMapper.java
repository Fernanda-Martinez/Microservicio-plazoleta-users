package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequestDto;
import com.pragma.powerup.domain.model.Usuarios;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IUsuarioRequestMapper {
    Usuarios toUsuarioModel(CrearUsuarioRequestDto usuarioRequest);

    @Mapping(source = "crearUsuarioRequestDto.idRol", target = "idRol")
    Usuarios toUser(CrearUsuarioRequestDto crearUsuarioRequestDto);
}
