package com.biblioswipe.backend.dto;

import java.time.LocalDate;

public class PerfilUpdateDTO {

    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String ciudad;
    private String fotoPerfil;

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }
}

