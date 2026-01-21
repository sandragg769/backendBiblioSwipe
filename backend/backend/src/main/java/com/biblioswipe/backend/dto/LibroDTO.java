package com.biblioswipe.backend.dto;

// para mostrar
public class LibroDTO {

    private Long id;
    private String titulo;
    private String autor;
    private String portada;
    private String categoriaNombre;

    public LibroDTO() {
    }

    public LibroDTO(Long id, String titulo, String autor, String portada, String categoriaNombre) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.portada = portada;
        this.categoriaNombre = categoriaNombre;
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

    public String getPortada() {
        return portada;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }
}
