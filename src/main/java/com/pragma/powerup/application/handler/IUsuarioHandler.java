package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequest;
import com.pragma.powerup.domain.model.Usuarios;

public interface IUsuarioHandler {

    Boolean crear(CrearUsuarioRequest usuarioRequest);
}
