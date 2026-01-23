package com.biblioswipe.backend.service;

import java.util.List;

import com.biblioswipe.backend.dto.CategoriaDTO;
import com.biblioswipe.backend.mapper.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioswipe.backend.model.Categoria;
import com.biblioswipe.backend.repository.CategoriaRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository,
                            CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    // METODOS CRUD
    // obtener todas las categorías
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDTO)
                .toList();
    }

    // obtener una categoría por id
    // BIEN
    public CategoriaDTO getCategoriaById(Long id) {
        Categoria cat = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        return categoriaMapper.toDTO(cat);
    }

    // METODOS DE LÓGICA DE NEGOCIO
    // buscar categoría por nombre
    // NO NECESARIO !!!!!
    public CategoriaDTO getCategoriaByNombre(String nombre) {
        Categoria categoria = categoriaRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        return categoriaMapper.toDTO(categoria);
    }


    // METODOS AUXILIARES
    private Categoria getCategoriaEntity(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
}
