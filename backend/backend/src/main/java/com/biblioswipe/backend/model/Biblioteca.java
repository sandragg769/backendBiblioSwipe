package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Biblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relación 1:1 con usuario
    // TENÍA EL FETCH LAZY ANTES DE CAMBIAR POR EXCEPTION DEL BACK DE SANDRA
    // Y SE HA AÑADIDO JSONIGNORE
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @JsonIgnore
    private Usuario usuario;

    // tres listas de libros (relación N:M con tabla intermedia)
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Libro> librosRecomendados = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Libro> librosLeidos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Libro> librosFuturasLecturas = new HashSet<>();

    // constructores
    public Biblioteca() {
    }

    public Biblioteca(Usuario usuario) {
        this.usuario = usuario;
    }


}
