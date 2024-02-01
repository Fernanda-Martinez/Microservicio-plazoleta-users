package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CrearUsuarioResponseDto {

    private String nombre;
    private String apellido;
    private int numeroDocumento;
    private String celular;
    private Date fechaNacimiento;
    private String correo;
    private String clave;
    private int idRol;


}
