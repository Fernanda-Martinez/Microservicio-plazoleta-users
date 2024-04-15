package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequestDto;
import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.AuthenticationResponseDto;
import com.pragma.powerup.application.handler.IUsuarioHandler;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final IUsuarioHandler userHandler;

    @Operation(summary = "Add new user")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Acción no válida para el usuario", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> crear(@RequestBody CrearUsuarioRequestDto crearUsuarioRequestDto){
        AuthenticationResponseDto authResponseDto = userHandler.crear(crearUsuarioRequestDto);
        if(authResponseDto.getToken().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponseDto);
    }

    @Operation(summary = "Login for the user")
    @ApiResponse(responseCode = "200", description = "Login success", content = @Content)
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        AuthenticationResponseDto resp = userHandler.login(loginRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resp);
    }
}