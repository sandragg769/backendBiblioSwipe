package com.biblioswipe.backend.dto;

// para buscador y biblioteca
public class LibroCreateDTO {

    private Long id;
    private String titulo;
    private String autor;
    private Long categoriaId;
    private String portada;

    public LibroCreateDTO() {
    }

    public LibroCreateDTO(Long id, String titulo, String autor, Long categoriaId, String portada) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoriaId = categoriaId;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getPortada() {
        return portada;
    }
}
