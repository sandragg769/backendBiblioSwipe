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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
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
