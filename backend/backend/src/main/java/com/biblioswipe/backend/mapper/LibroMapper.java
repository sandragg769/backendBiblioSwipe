package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.LibroDTO;
import com.biblioswipe.backend.model.Libro;
import org.springframework.stereotype.Component;

@Component
public class LibroMapper {

    public LibroDTO toDTO(Libro libro) {
        return new LibroDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getPortada(),
                libro.getCategoria() != null
                        ? libro.getCategoria().getNombre()
                        : null
        );
    }
}

