package com.biblioswipe.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    

}
// acceso a la base de datos
// automáticamente crea save, findAll, findById, save (actualizar) y deleteById
