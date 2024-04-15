package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.model.Usuarios;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;

public class UsuarioUseCase implements IUsuarioServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
    }

    @Override
    public Boolean crear(Usuarios usuario) {
        this.usuarioPersistencePort.crear(usuario);

        return true;
    }

    @Override
    public boolean validateOwnerRole(int id) {
        return this.usuarioPersistencePort.validateOwnerRole(id);
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
}
