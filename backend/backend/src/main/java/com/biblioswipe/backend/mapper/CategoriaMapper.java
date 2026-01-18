package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.CategoriaDTO;
import com.biblioswipe.backend.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) return null;

        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre()
        );
    }

    public Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) return null;

        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNombre(dto.getNombre());
        return categoria;
    }
}

