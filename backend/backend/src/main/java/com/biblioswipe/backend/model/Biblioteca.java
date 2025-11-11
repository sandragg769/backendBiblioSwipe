package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "biblioteca")
public class Biblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relación 1:1 con usuario
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    // indica que este lado será ignorado durante la serialización
    @JsonBackReference
    private Usuario usuario;

    // tres listas de libros (relación N:M con tabla intermedia)
    @ManyToMany
    @JoinTable(name = "biblioteca_recomendados", joinColumns = @JoinColumn(name = "biblioteca_id"), inverseJoinColumns = @JoinColumn(name = "libro_id"))
    private Set<Libro> librosRecomendados = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "biblioteca_leidos", joinColumns = @JoinColumn(name = "biblioteca_id"), inverseJoinColumns = @JoinColumn(name = "libro_id"))
    private Set<Libro> librosLeidos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "biblioteca_futuras", joinColumns = @JoinColumn(name = "biblioteca_id"), inverseJoinColumns = @JoinColumn(name = "libro_id"))
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
