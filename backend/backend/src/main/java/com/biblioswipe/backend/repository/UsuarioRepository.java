package com.biblioswipe.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.usuarioId != :miId " +
            "AND u.usuarioId NOT IN (SELECT f.usuarioId FROM Usuario m JOIN m.usuariosFavoritos f WHERE m.usuarioId = :miId)")
    List<Usuario> findCandidatosParaPrincipal(@Param("miId") Long miId);

}
// acceso a la base de datos
// automáticamente crea save, findAll, findById, save (actualizar) y deleteById
