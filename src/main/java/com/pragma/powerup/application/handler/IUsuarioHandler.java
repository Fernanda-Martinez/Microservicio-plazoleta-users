package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequestDto;
import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.AuthenticationResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.infrastructure.exception.PasswordIncorrectException;

public interface IUsuarioHandler {

    AuthenticationResponseDto crear(CrearUsuarioRequestDto usuarioRequest);

    AuthenticationResponseDto login(LoginRequestDto loginRequestDto) throws PasswordIncorrectException;

    boolean validateOwnerRole(int id);

    boolean validateRestaurantEmployee(int idEmployee, int idRestaurant);

    boolean validateAdminRole(int id);

    boolean validateClientRole(int id);

    UserInfoResponseDto getUser(int idUsuario);
}
