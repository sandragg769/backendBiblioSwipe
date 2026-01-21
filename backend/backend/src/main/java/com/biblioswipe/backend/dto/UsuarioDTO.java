package com.biblioswipe.backend.dto;

// salida al front
public class UsuarioDTO {
    private Long id;
    private String email;
    private PerfilDTO perfil;

    public UsuarioDTO(Long id, String email, PerfilDTO perfil) {
        this.id = id;
        this.email = email;
        this.perfil = perfil;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public PerfilDTO getPerfil() {
        return perfil;
    }
}
