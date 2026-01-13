package com.biblioswipe.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    // obtener una categoría por id
    public Categoria getCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    // crear una categoría
    public Categoria createCategoria(Categoria categoria) {
        // Evitar duplicados por nombre
        categoriaRepository.findByNombreIgnoreCase(categoria.getNombre())
                .ifPresent(c -> {
                    throw new RuntimeException("La categoría ya existe");
                });

        return categoriaRepository.save(categoria);
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
        Categoria categoria = getCategoriaById(id);

        if (!categoria.getLibros().isEmpty()) {
            throw new RuntimeException("No se puede eliminar una categoría con libros asociados");
        }

        categoriaRepository.deleteById(id);
    }

    // METODOS DE LÓGICA DE NEGOCIO
    // obtener libros por categorías (para el filtrado)
    public List<Libro> getLibrosByCategoria(Long categoriaId) {
        Categoria categoria = getCategoriaById(categoriaId);
        return categoria.getLibros().stream().toList();
    }

    // buscar categoría por nombre
    public Categoria getCategoriaByNombre(String nombre) {
        return categoriaRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
}
