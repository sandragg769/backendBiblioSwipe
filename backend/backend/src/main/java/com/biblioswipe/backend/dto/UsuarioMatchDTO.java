package com.biblioswipe.backend.dto;

import com.biblioswipe.backend.model.Usuario;

// para filtrado de categoria en home
public class UsuarioMatchDTO {

    private Long usuarioId;
    private String nombre;
    private String ciudad;
    private String fotoPerfil;
    private long coincidencias;

    public UsuarioMatchDTO(Usuario usuario, long coincidencias) {
        this.usuarioId = usuario.getUsuario_id();
        this.coincidencias = coincidencias;

        if (usuario.getPerfil() != null) {
            this.nombre = usuario.getPerfil().getNombre();
            this.ciudad = usuario.getPerfil().getCiudad();
            this.fotoPerfil = usuario.getPerfil().getFotoPerfil();
        }
    }

    // getters
}
