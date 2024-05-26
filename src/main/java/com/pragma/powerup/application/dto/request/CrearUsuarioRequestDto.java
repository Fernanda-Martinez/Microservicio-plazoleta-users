package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter

public class CrearUsuarioRequestDto {

    private String nombre;
    private String apellido;
    private int numeroDocumento;

    @Size(max = 13, message = "El número de celular no es válido")
    @Pattern(regexp = "[0-9+]+", message = "El numero de celular solo debe contener caracteres numericos, con excepcion del simbolo +")
    private String celular;

    @Min(value = 18, message = "El usuario debe ser mayor de edad")
    private Date fechaNacimiento;
    @Email(message = "El correo no es válido")
    private String correo;
    private String clave;
    private int idRol;
    private String token;
    private Integer idRestaurante = null;
}
