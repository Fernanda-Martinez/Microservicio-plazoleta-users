package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Usuarios;

public interface IUsuarioPersistencePort {

    Usuarios crear(Usuarios usuario);

    boolean validateOwnerRole(int id);

    boolean validateRestaurantEmployee(int idEmployee, int idRestaurant);

    boolean validateClientRole(int idClient);

    Usuarios getUser(int idUser);
}
