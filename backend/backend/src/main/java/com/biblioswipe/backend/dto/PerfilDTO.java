package com.biblioswipe.backend.dto;

import java.time.LocalDate;

public class PerfilDTO {

    private Long perfilId;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String ciudad;
    private String fotoPerfil;
    // private UsuarioDTO usuario;
    public PerfilDTO(){}

    public PerfilDTO(
            Long perfilId,
            String nombre,
            String apellidos,
            LocalDate fechaNacimiento,
            String ciudad,
            String fotoPerfil
            //UsuarioDTO usuario
    ) {
        this.perfilId = perfilId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
        this.fotoPerfil = fotoPerfil;
        //this.usuario = usuario;
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

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

}

