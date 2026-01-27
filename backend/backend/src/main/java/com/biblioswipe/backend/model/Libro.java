package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private String portada;

    // relacion 1:N con categoria (solo uan categoría el libro)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("libros")
    private Categoria categoria;


    // relaciones N:M con biblioteca (en biblioteca hay tres listas de libros
    // también)
    // NO debe conocer bibliotecas
    @ManyToMany(mappedBy = "librosRecomendados", fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Biblioteca> bibliotecasRecomendados;

    // NO debe conocer bibliotecas
    @ManyToMany(mappedBy = "librosLeidos", fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Biblioteca> bibliotecasLeidos;

    @ManyToMany(mappedBy = "librosFuturasLecturas", fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Biblioteca> bibliotecasFuturasLecturas;

    // constructores
    public Libro() {
    }

    public Libro(String titulo, String autor, String portada, Categoria categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.portada = portada;
        this.categoria = categoria;
    }
}
