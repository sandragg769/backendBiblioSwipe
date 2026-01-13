package com.biblioswipe.backend.dto;

import java.time.LocalDate;

public class PerfilDTO {

    private Long perfilId;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String ciudad;
    private String fotoPerfil;
    private UsuarioPerfilDTO usuario;

    public PerfilDTO() {
    }

    public PerfilDTO(
            Long perfilId,
            String nombre,
            String apellidos,
            LocalDate fechaNacimiento,
            String ciudad,
            String fotoPerfil,
            UsuarioPerfilDTO usuario
    ) {
        this.perfilId = perfilId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
        this.fotoPerfil = fotoPerfil;
        this.usuario = usuario;
    }

    public Long getPerfilId() {
        return perfilId;
    }

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

    public UsuarioPerfilDTO getUsuario() {
        return usuario;
    }
}
