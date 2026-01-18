package com.biblioswipe.backend.service;

import java.util.List;

import com.biblioswipe.backend.dto.CategoriaDTO;
import com.biblioswipe.backend.mapper.CategoriaMapper;
import org.springframework.stereotype.Service;

import com.biblioswipe.backend.model.Categoria;
import com.biblioswipe.backend.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository,
                            CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    // METODOS CRUD
    // obtener todas las categorías
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDTO)
                .toList();
    }

    // obtener una categoría por id
    public CategoriaDTO getCategoriaById(Long id) {
        return categoriaMapper.toDTO(getCategoriaEntity(id));
    }

    // crear una categoría
    public CategoriaDTO createCategoria(CategoriaDTO dto) {
        categoriaRepository.findByNombreIgnoreCase(dto.getNombre())
                .ifPresent(c -> {
                    throw new RuntimeException("La categoría ya existe");
                });

        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toDTO(
                categoriaRepository.save(categoria)
        );
    }

    // actualizar una categoría concreta
    // NO SE NECESITA, LO DEJO POR SI ACASO
    public CategoriaDTO updateCategoria(Long id, CategoriaDTO dto) {
        Categoria existente = getCategoriaEntity(id);
        existente.setNombre(dto.getNombre());

        return categoriaMapper.toDTO(
                categoriaRepository.save(existente)
        );
    }

    // borrar una categoría concreta
    // NO SE NECESITA, LO DEJO POR SI ACASO
    public void deleteCategoria(Long id) {
        Categoria categoria = getCategoriaEntity(id);

        if (!categoria.getLibros().isEmpty()) {
            throw new RuntimeException("No se puede eliminar una categoría con libros asociados");
        }

        categoriaRepository.delete(categoria);
    }


    // METODOS DE LÓGICA DE NEGOCIO
    // buscar categoría por nombre
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
