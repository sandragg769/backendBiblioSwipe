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

    // getters y setters


    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCoincidencias() {
        return coincidencias;
    }

    public void setCoincidencias(long coincidencias) {
        this.coincidencias = coincidencias;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
