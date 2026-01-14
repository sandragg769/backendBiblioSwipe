package com.biblioswipe.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.biblioswipe.backend.dto.CategoriaDTO;
import org.springframework.stereotype.Service;

import com.biblioswipe.backend.model.Categoria;
import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // METODOS CRUD
    // obtener todas las categorías
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // obtener una categoría por id
    public CategoriaDTO getCategoriaById(Long id) {
        return toDTO(getCategoriaEntity(id));
    }

    // crear una categoría
    public CategoriaDTO createCategoria(Categoria categoria) {
        categoriaRepository.findByNombreIgnoreCase(categoria.getNombre())
                .ifPresent(c -> {
                    throw new RuntimeException("La categoría ya existe");
                });

        return toDTO(categoriaRepository.save(categoria));
    }

    // actualizar una categoría concreta
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
    public Categoria updateCategoria(Long id, Categoria actualizada) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        existente.setNombre(actualizada.getNombre());
        return categoriaRepository.save(existente);
    }

    // borrar una categoría concreta
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
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
        return toDTO(categoria);
    }

    // metodos auxiliares
    private Categoria getCategoriaEntity(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre()
        );
    }
}
