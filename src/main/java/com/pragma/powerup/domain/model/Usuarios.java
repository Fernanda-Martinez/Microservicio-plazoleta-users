package com.pragma.powerup.domain.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Usuarios {

    private int id;
    private String nombre;
    private String apellido;
    private int numeroDocumento;
    private String celular;
    private Date fechaNacimiento;
    private String correo;
    private String clave;
    private int idRol;
    private Integer idRestaurante;

}
