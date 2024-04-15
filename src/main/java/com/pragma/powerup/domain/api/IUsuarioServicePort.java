package com.pragma.powerup.domain.api;


import com.pragma.powerup.domain.model.Usuarios;

public interface IUsuarioServicePort {

    Boolean crear(Usuarios user);

    boolean validateOwnerRole(int id);

    boolean validateRestaurantEmployee(int idEmployee, int idRestaurant);

    boolean validateClientRole(int idClient);

    Usuarios getUser(int idUser);
}