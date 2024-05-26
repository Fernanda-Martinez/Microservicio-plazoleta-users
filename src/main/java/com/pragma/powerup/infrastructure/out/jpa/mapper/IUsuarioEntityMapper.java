package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Usuarios;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioEntityMapper {
    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.nombre", target = "nombre")
    UsuarioEntity toEntidad(Usuarios user);

    @Mapping(source = "userEntity.idRol", target = "idRol")
   // @Mapping(source = "restaurantId", target = "restaurantId")
    Usuarios toUsuario(UsuarioEntity userEntity);



}