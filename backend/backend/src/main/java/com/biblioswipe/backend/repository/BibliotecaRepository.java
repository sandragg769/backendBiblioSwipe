package com.biblioswipe.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioswipe.backend.model.Biblioteca;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
}
