package com.biblioswipe.backend.dto;

import java.util.Set;

// editar biblioteca
public class BibliotecaDTO {

    private Set<LibroCreateDTO> recomendados;
    private Set<LibroCreateDTO> leidos;
    private Set<LibroCreateDTO> futurasLecturas;

    public BibliotecaDTO() {
    }

    public BibliotecaDTO(
            Set<LibroCreateDTO> recomendados,
            Set<LibroCreateDTO> leidos,
            Set<LibroCreateDTO> futurasLecturas
    ) {
        this.recomendados = recomendados;
        this.leidos = leidos;
        this.futurasLecturas = futurasLecturas;
    }

    public BibliotecaDTO(Long id, Set<LibroCreateDTO> libroCreateDTOSet, Set<LibroCreateDTO> libroCreateDTOSet1, Set<LibroCreateDTO> libroCreateDTOSet2) {
    }

    public Set<LibroCreateDTO> getRecomendados() {
        return recomendados;
    }

    public Set<LibroCreateDTO> getLeidos() {
        return leidos;
    }

    public Set<LibroCreateDTO> getFuturasLecturas() {
        return futurasLecturas;
    }
}
