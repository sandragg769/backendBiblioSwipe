package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "biblioteca")
public class Biblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relación 1:1 con usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    // tres listas de libros (relación N:M con tabla intermedia)
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Libro> librosRecomendados = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Libro> librosLeidos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Libro> librosFuturasLecturas = new HashSet<>();

    // constructores
    public Biblioteca() {
    }

    public Biblioteca(Usuario usuario) {
        this.usuario = usuario;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Libro> getLibrosRecomendados() {
        return librosRecomendados;
    }

    public void setLibrosRecomendados(Set<Libro> librosRecomendados) {
        this.librosRecomendados = librosRecomendados;
    }

    public Set<Libro> getLibrosLeidos() {
        return librosLeidos;
    }

    public void setLibrosLeidos(Set<Libro> librosLeidos) {
        this.librosLeidos = librosLeidos;
    }

    public Set<Libro> getLibrosFuturasLecturas() {
        return librosFuturasLecturas;
    }

    public void setFuturasLecturas(Set<Libro> librosFuturasLecturas) {
        this.librosFuturasLecturas = librosFuturasLecturas;
    }

}
