package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.model.Usuarios;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.infrastructure.exception.UserAlreadyExistsException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;


    @Override
    public Usuarios crear(Usuarios usuario) {

        if(usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        UsuarioEntity nuevoUsuario = usuarioRepository.save(usuarioEntityMapper.toEntidad(usuario));
        return usuarioEntityMapper.toUsuario(nuevoUsuario);
    }

    @Override
    public boolean validateOwnerRole(int id) {
        Optional<UsuarioEntity> ownerInfo = usuarioRepository.findById(id);
        if(ownerInfo.isPresent()){
            UsuarioEntity owner = ownerInfo.get();
            return getRolName(owner.getIdRol()).equals(Constants.OWNER);
        }
        return false;
    }

    @Override
    public boolean validateRestaurantEmployee(int idEmployee, int idRestaurant) {
        return false;
    }

    @Override
    public boolean validateClientRole(int idClient) {
        return false;
    }

    @Override
    public Usuarios getUser(int idUser) {
        return null;
    }


    private String getRolName(int id) {
        switch (id) {
            case 1:
                return Constants.REGISTER_ROLE_ADMIN;
            case 2:
                return Constants.OWNER;
            case 3:
                return Constants.EMPLOYEE;
            default:
                return Constants.CLIENT;
        }
    }
}
