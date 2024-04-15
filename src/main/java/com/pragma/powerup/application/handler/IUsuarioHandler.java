package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequestDto;
import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.AuthenticationResponseDto;
import com.pragma.powerup.application.dto.response.UsuarioInfoResponseDto;

public interface IUsuarioHandler {

    AuthenticationResponseDto crear(CrearUsuarioRequestDto usuarioRequest);

    AuthenticationResponseDto login(LoginRequestDto loginRequestDto);

    boolean validateOwnerRole(int id);

    boolean validateRestaurantEmployee(int idEmployee, int idRestaurant);

    UsuarioInfoResponseDto getClient(int idClient);
}
