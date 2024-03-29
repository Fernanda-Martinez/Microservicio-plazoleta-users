package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequestDto;
import com.pragma.powerup.application.handler.impl.UsuarioHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioRestController {

    private final UsuarioHandler usuarioHandler;

    @Operation(summary = "Agrega un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "usuario creado", content = @Content),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Boolean> crear(@RequestBody CrearUsuarioRequestDto usuarioRequest) {
        Boolean user = usuarioHandler.crear(usuarioRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
