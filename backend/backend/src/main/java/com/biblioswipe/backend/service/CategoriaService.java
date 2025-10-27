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
    public Optional<Categoria> getCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    // crear una categoría
    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // actualizar una categoría concreta
    //REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENACIÓN ???????
    public Categoria updateCategoria(Long id, Categoria actualizada) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        existente.setNombre(actualizada.getNombre());
        return categoriaRepository.save(existente);
    }

    // borrar una categoría concreta
    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    // METODOS DE LÓGICA DE NEGOCIO
    // obtener libros por categorías (para el filtrado)
    public List<Libro> getLibrosByCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return new ArrayList<>(categoria.getLibros());
    }

    // buscar categoría por nombre
    public Optional<Categoria> findByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
}
