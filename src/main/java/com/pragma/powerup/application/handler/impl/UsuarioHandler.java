package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequest;
import com.pragma.powerup.application.handler.IUsuarioHandler;
import com.pragma.powerup.application.mapper.IUsuarioRequestMapper;
import com.pragma.powerup.application.mapper.IUsuarioResponseMapper;
import com.pragma.powerup.application.services.PassEncoder;
import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.model.Usuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioHandler implements IUsuarioHandler {

    private final IUsuarioServicePort servicePort;
    private final IUsuarioRequestMapper usuarioRequestMapper;
    private final IUsuarioResponseMapper usuarioResponseMapper;

    public UsuarioHandler(IUsuarioServicePort servicePort, IUsuarioRequestMapper usuarioRequestMapper, IUsuarioResponseMapper usuarioResponseMapper) {
        this.servicePort = servicePort;
        this.usuarioRequestMapper = usuarioRequestMapper;
        this.usuarioResponseMapper = usuarioResponseMapper;
    }


    @Override
    public Boolean crear(CrearUsuarioRequest usuarioRequest) {
        Usuarios usuario = usuarioRequestMapper.toUsuarioModel(usuarioRequest);

        String claveEncriptada = PassEncoder.passwordEncoder(usuario.getClave());
        usuario.setClave(claveEncriptada);

        servicePort.crear(usuario);

        return true;
    }

}
