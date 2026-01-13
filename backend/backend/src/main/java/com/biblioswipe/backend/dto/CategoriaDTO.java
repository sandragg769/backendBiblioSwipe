package com.biblioswipe.backend.dto;

// para filtro (desplegable)
public class CategoriaDTO {

    private Long id;
    private String nombre;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
