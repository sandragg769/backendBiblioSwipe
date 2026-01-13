package com.biblioswipe.backend.dto;

// pantalla principal
public class UsuarioSwipeDTO {

    private Long usuarioId;
    private String nombre;
    private String ciudad;
    private String fotoPerfil;

    public UsuarioSwipeDTO() {
    }

    public UsuarioSwipeDTO(Long usuarioId, String nombre, String ciudad, String fotoPerfil) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.fotoPerfil = fotoPerfil;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }
}
