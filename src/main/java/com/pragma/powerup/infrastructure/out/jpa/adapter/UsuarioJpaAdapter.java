package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.model.Usuarios;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.UserAlreadyExistsException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
    public boolean validateAdminRole(int id) {
        Optional<UsuarioEntity> adminInfo = usuarioRepository.findById(id);
        if(adminInfo.isPresent()){
            UsuarioEntity admin = adminInfo.get();
            return getRolName(admin.getIdRol()).equals(Constants.REGISTER_ROLE_ADMIN);
        }
        return false;
    }

    @Override
    public boolean validateRestaurantEmployee(int idEmployee, int idRestaurant) {
        Optional<UsuarioEntity> empleado = usuarioRepository.findById(idEmployee);

        if(empleado.get().getIdRestaurante() == idRestaurant){
            return true;
        }
        return false;
    }

    @Override
    public boolean validateClientRole(int idClient) {

        Optional<UsuarioEntity> clientInfo = usuarioRepository.findById(idClient);
        if(clientInfo.isPresent()){
            UsuarioEntity client = clientInfo.get();
            return getRolName(client.getIdRol()).equals(Constants.CLIENT);
        }
        return false;

    }

    @Override
    public Usuarios getUser(int idUser) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(idUser);

        if(usuarioEntity.isEmpty()){
            throw new UsernameNotFoundException("El usuario no existe");
        }


        return usuarioEntityMapper.toUsuario(usuarioEntity.get());
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
