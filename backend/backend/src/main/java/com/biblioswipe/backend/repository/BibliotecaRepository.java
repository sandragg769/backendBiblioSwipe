package com.biblioswipe.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioswipe.backend.model.Biblioteca;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
    @Query("""
                SELECT b
                FROM Biblioteca b
                WHERE b.usuario.usuarioId = :usuarioId
            """)
    Optional<Biblioteca> findByUsuarioId(Long usuarioId);

}
