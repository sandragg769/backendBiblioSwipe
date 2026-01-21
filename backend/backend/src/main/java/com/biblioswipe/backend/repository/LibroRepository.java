package com.biblioswipe.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioswipe.backend.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByAutorContainingIgnoreCase(String autor);

    // Para la barra de búsqueda (LibroScreen.kt)
    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor);

    // Para filtrar por categorías (Categoria.kt)
    List<Libro> findByCategoriaId(Long categoriaId);

    // Para evitar duplicados al crear libros
    boolean existsByTituloAndAutor(String titulo, String autor);
}
