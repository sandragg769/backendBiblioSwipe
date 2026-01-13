package com.biblioswipe.backend.dto;

// para la ventana del corazón
public class FavoritoDTO {

    private Long usuarioId;
    private String nombre;
    private String fotoPerfil;

    public FavoritoDTO() {
    }

    public FavoritoDTO(Long usuarioId, String nombre, String fotoPerfil) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }
}
