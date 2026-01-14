package com.biblioswipe.backend.dto;

import java.util.Set;

// editar biblioteca
public class BibliotecaDTO {

    private Set<LibroDTO> recomendados;
    private Set<LibroDTO> leidos;
    private Set<LibroDTO> futurasLecturas;

    public BibliotecaDTO() {
    }

    public BibliotecaDTO(
            Set<LibroDTO> recomendados,
            Set<LibroDTO> leidos,
            Set<LibroDTO> futurasLecturas
    ) {
        this.recomendados = recomendados;
        this.leidos = leidos;
        this.futurasLecturas = futurasLecturas;
    }

    public BibliotecaDTO(Long id, Set<LibroDTO> libroDTOSet, Set<LibroDTO> libroDTOSet1, Set<LibroDTO> libroDTOSet2) {
    }

    public Set<LibroDTO> getRecomendados() {
        return recomendados;
    }

    public Set<LibroDTO> getLeidos() {
        return leidos;
    }

    public Set<LibroDTO> getFuturasLecturas() {
        return futurasLecturas;
    }
}
