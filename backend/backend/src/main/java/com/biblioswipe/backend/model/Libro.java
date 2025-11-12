package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private String portada;

    // relacion 1:N con categoria (solo uan categoría el libro)
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonBackReference // evita bucle con Categoria
    private Categoria categoria;

    // relaciones N:M con biblioteca (en biblioteca hay tres listas de libros
    // también)
    @ManyToMany(mappedBy = "librosRecomendados")
    @JsonBackReference //evita recursividad circular entre Libro y Biblioteca
    private Set<Biblioteca> bibliotecasRecomendados = new HashSet<>();

    @ManyToMany(mappedBy = "librosLeidos")
    @JsonBackReference
    private Set<Biblioteca> bibliotecasLeidos = new HashSet<>();

    @ManyToMany(mappedBy = "librosFuturasLecturas")
    @JsonBackReference
    private Set<Biblioteca> bibliotecasFuturasLecturas = new HashSet<>();

    // constructores
    public Libro() {
    }

    public Libro(String titulo, String autor, String portada, Categoria categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.portada = portada;
        this.categoria = categoria;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Biblioteca> getBibliotecasRecomendados() {
        return bibliotecasRecomendados;
    }

    public void setBibliotecasRecomendados(Set<Biblioteca> bibliotecasRecomendados) {
        this.bibliotecasRecomendados = bibliotecasRecomendados;
    }

    public Set<Biblioteca> getBibliotecasLeidos() {
        return bibliotecasLeidos;
    }

    public void setBibliotecasLeidos(Set<Biblioteca> bibliotecasLeidos) {
        this.bibliotecasLeidos = bibliotecasLeidos;
    }

    public Set<Biblioteca> getBibliotecasFuturasLecturas() {
        return bibliotecasFuturasLecturas;
    }

    public void setBibliotecasFuturasLecturas(Set<Biblioteca> bibliotecasFuturasLecturas) {
        this.bibliotecasFuturasLecturas = bibliotecasFuturasLecturas;
    }

}
