package com.biblioswipe.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioswipe.backend.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query("SELECT p FROM Perfil p WHERE p.usuario.usuario_id = :usuarioId")
    Optional<Perfil> findByUsuario_Id(@Param("usuarioId") Long usuarioId);

    List<Perfil> findByCiudadIgnoreCase(String ciudad);

    List<Perfil> findByCiudad(String ciudad);

}
