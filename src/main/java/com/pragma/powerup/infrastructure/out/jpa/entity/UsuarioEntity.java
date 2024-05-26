package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(length = 45)
    private String nombre;
    @Column(length = 45)
    private String apellido;
    private int numeroDocumento;
    @Column(length = 45)
    private String celular;
    private Date fechaNacimiento;
    @Column(length = 45)
    private String correo;
    @Column(length = 45)
    private String clave;
    private int idRol;
    private Integer idRestaurante;

}
