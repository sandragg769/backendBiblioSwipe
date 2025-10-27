package com.biblioswipe.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioswipe.backend.model.Categoria;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}
