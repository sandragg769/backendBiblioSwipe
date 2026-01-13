package com.biblioswipe.backend.dto;

// sin password
public class UsuarioPerfilDTO {

    private Long id;
    private String email;

    public UsuarioPerfilDTO() {
    }

    public UsuarioPerfilDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}


