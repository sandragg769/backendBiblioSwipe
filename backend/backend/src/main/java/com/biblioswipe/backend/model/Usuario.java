package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "usuario_id"
)
//representa los datos y las entidades
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuario_id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // relación 1:1 con perfil
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Perfil perfil;

    // relación 1:1 con la biblioteca
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Biblioteca biblioteca;

    // relación N:M con categorias
    // categorías favoritas (filtro)
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Categoria> categorias = new HashSet<>();

    // Relación 1:N reflexiva (usuarios favoritos)
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Usuario> usuariosFavoritos = new HashSet<>();

    @ManyToMany(mappedBy = "usuariosFavoritos", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Usuario> esFavoritoDe = new HashSet<>();

    // constructores
    public Usuario() {
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // getters y setters
    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Usuario> getUsuariosFavoritos() {
        return usuariosFavoritos;
    }

    public void setUsuariosFavoritos(Set<Usuario> usuariosFavoritos) {
        this.usuariosFavoritos = usuariosFavoritos;
    }

    public Set<Usuario> getEsFavoritoDe() {
        return esFavoritoDe;
    }

    public void setEsFavoritoDe(Set<Usuario> esFavoritoDe) {
        this.esFavoritoDe = esFavoritoDe;
    }
}