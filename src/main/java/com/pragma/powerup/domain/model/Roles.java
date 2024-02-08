package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


public class Roles {
    private int id;
    private String nombre;
    private String descripcion;

}
