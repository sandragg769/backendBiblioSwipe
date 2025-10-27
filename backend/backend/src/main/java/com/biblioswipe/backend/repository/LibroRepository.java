package com.biblioswipe.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioswipe.backend.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // para encontrar libros por categorias
    List<Libro> findByCategoria_NombreIgnoreCase(String nombre);

    List<Libro> findByAutorContainingIgnoreCase(String autor);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);
}
