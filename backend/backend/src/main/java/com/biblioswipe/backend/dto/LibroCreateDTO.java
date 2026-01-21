package com.biblioswipe.backend.dto;

// para buscador y biblioteca (crear añadir a biblioteca)
public class LibroCreateDTO {

    // quitar id
    private String titulo;
    private String autor;
    private Long categoriaId;
    private String portada;

    public LibroCreateDTO() {
    }

    public LibroCreateDTO(String titulo, String autor, Long categoriaId, String portada) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoriaId = categoriaId;
        this.portada = portada;
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
