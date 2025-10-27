package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // relación 1:N con libro (muchos libros pueden tener la misma categoría)
    @OneToMany(mappedBy = "categoria")
    private Set<Libro> libros = new HashSet<>();

    // relación N:M con usuario
    @ManyToMany(mappedBy = "categorias")
    private Set<Usuario> usuarios = new HashSet<>();

    // constructor
    public Categoria() {
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
