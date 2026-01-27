package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "usuarioId"
)
//representa los datos y las entidades
@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    // relación 1:1 con perfil
    // QUITAR JSONIGNORE DESPUÉS DE EXCEPTION DE SANDRA
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Perfil perfil;

    // relación 1:1 con la biblioteca
    // QUITAR JSONIGNORE DESPUÉS DE EXCEPTION DE SANDRA
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
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
    @EqualsAndHashCode.Exclude
    private Set<Usuario> esFavoritoDe = new HashSet<>();

    // constructores
    public Usuario() {
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }
}