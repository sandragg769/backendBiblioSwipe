package com.biblioswipe.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioswipe.backend.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    // para localizar perfiles por ciudad
    List<Perfil> findByCiudadIgnoreCase(String ciudad);
}
