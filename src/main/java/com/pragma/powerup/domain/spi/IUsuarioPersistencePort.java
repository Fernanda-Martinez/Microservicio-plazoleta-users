package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Usuarios;

public interface IUsuarioPersistencePort {

    Usuarios crear(Usuarios usuario);
}
