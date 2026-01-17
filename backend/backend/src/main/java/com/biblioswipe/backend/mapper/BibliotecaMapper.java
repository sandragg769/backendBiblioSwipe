package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.BibliotecaDTO;
import com.biblioswipe.backend.model.Biblioteca;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BibliotecaMapper {

    private final LibroMapper libroMapper;

    public BibliotecaMapper(LibroMapper libroMapper) {
        this.libroMapper = libroMapper;
    }

    public BibliotecaDTO toDTO(Biblioteca biblioteca) {
        return new BibliotecaDTO(
                biblioteca.getLibrosRecomendados()
                        .stream()
                        .map(libroMapper::toDTO)
                        .collect(Collectors.toSet()),

                biblioteca.getLibrosLeidos()
                        .stream()
                        .map(libroMapper::toDTO)
                        .collect(Collectors.toSet()),

                biblioteca.getLibrosFuturasLecturas()
                        .stream()
                        .map(libroMapper::toDTO)
                        .collect(Collectors.toSet())
        );
    }
}

