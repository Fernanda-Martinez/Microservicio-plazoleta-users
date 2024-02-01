package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequestDto;

public interface IUsuarioHandler {

    Boolean crear(CrearUsuarioRequestDto usuarioRequest);
}
