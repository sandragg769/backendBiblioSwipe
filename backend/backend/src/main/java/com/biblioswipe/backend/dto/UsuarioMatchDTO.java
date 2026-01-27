package com.biblioswipe.backend.dto;

import com.biblioswipe.backend.model.Usuario;

// para filtrado de categoria en home
public class UsuarioMatchDTO {

    private Long usuarioId;
    //private String nombre;
    //private String ciudad;
    //private String fotoPerfil;
    private long coincidencias;

    public UsuarioMatchDTO(Usuario usuario, long coincidencias) {
        this.usuarioId = usuario.getUsuarioId();
        this.coincidencias = coincidencias;
    }

    // getters y setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public long getCoincidencias() {
        return coincidencias;
    }

    public void setCoincidencias(long coincidencias) {
        this.coincidencias = coincidencias;
    }
}
