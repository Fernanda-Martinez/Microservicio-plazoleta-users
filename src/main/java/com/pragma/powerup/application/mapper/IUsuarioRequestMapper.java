package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequest;
import com.pragma.powerup.application.dto.request.ObjectRequestDto;
import com.pragma.powerup.domain.model.ObjectModel;
import com.pragma.powerup.domain.model.Usuarios;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IUsuarioRequestMapper {
    Usuarios toUsuarioModel(CrearUsuarioRequest usuarioRequest);
}
