package com.biblioswipe.backend.dto;

// para buscador y biblioteca
public class LibroDTO {

    private Long id;
    private String titulo;
    private String autor;
    private String categoria;
    private String portada;

    public LibroDTO() {
    }

    public LibroDTO(Long id, String titulo, String autor, String categoria, String portada) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.portada = portada;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPortada() {
        return portada;
    }
}
