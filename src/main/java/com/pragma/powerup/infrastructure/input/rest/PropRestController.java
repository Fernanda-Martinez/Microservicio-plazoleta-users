package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.IUsuarioHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
@RequiredArgsConstructor
public class PropRestController {
    private final IUsuarioHandler usuarioHandler;

    @Operation(summary = "Validar si el usuario es un propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es un propietario", content = @Content),
            @ApiResponse(responseCode = "401", description = "No es un propietario", content = @Content),
            @ApiResponse(responseCode = "403", description = "NO TIENES PERMISOS", content = @Content)
    })
    @GetMapping("/owner/{id}")
    public ResponseEntity<Boolean> validateOwnerRole(@PathVariable int id){
        boolean ownerRole = usuarioHandler.validateOwnerRole(id);
        if(ownerRole){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(true);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(false);
    }

    //validar admin
    @Operation(summary = "Validar si el usuario es un admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es un administrador", content = @Content),
            @ApiResponse(responseCode = "401", description = "No es un administrador", content = @Content),
            @ApiResponse(responseCode = "403", description = "NO TIENES PERMISOS", content = @Content)
    })
    @GetMapping("/admin/{id}")
    public ResponseEntity<Boolean> validateAdminRole(@PathVariable int id){
        boolean adminRole = usuarioHandler.validateAdminRole(id);
        if(adminRole){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(true);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(false);
    }

    //validar cliente
    @Operation(summary = "Validar si el usuario es un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es un Cliente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No es un Cliente", content = @Content),
            @ApiResponse(responseCode = "403", description = "NO TIENES PERMISOS", content = @Content)
    })
    @GetMapping("/client/{id}")
    public ResponseEntity<Boolean> validateClientRole(@PathVariable int id){
        boolean clientRole = usuarioHandler.validateClientRole(id);
        if(clientRole){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(true);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(false);
    }

    //validar empleado de un restaurante
    @Operation(summary = "Validar si el empleado pertenece al restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respuesta de la cuestión", content = @Content),
            @ApiResponse(responseCode = "403", description = "NO TIENES PERMISOS", content = @Content)
    })
    @GetMapping("/employeeRestaurant/{idEmpleado}/{idRestaurante}")
    public ResponseEntity<Boolean> validateEmployeeRestaurant(@PathVariable int idEmpleado, @PathVariable int idRestaurante){
        boolean clientRole = usuarioHandler.validateRestaurantEmployee(idEmpleado, idRestaurante);
        if(clientRole){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(true);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(false);
    }

    @Operation(summary = "Obtiene la info del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respuesta de la cuestión", content = @Content),
            @ApiResponse(responseCode = "403", description = "NO TIENES PERMISOS", content = @Content)
    })
    @GetMapping("/get_user/{idUsuario}")
    public ResponseEntity<UserInfoResponseDto> getUser(@PathVariable int idUsuario){
        UserInfoResponseDto user = usuarioHandler.getUser(idUsuario);
            return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
