package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.repository.PerfilRepository;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    // METODOS CRUD
    // obtener todos los perfiles
    public List<Perfil> getAllPerfiles() {
        return perfilRepository.findAll();
    }

    // obtener un perfil en concreto por id
    public Optional<Perfil> getPerfilById(Long id) {
        return perfilRepository.findById(id);
    }

    // crear un perfil (a la hora de la creación de la cuenta)
    public Perfil createPerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    // actualizar perfil concreto
    public Perfil updatePerfil(Long id, Perfil actualizado) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        perfil.setNombre(actualizado.getNombre());
        perfil.setApellidos(actualizado.getApellidos());
        perfil.setCiudad(actualizado.getCiudad());
        perfil.setFotoPerfil(actualizado.getFotoPerfil());
        perfil.setFechaNacimiento(actualizado.getFechaNacimiento());
        return perfilRepository.save(perfil);
    }

    // eliminar perfil concreto
    public void deletePerfil(Long id) {
        perfilRepository.deleteById(id);
    }

    // METODOS DE LÓGICA DE NEGOCIO
    // localizar perfiles con la misma ciudad
    public List<Perfil> findByCiudad(String ciudad) {
        return perfilRepository.findByCiudadIgnoreCase(ciudad);
    }

}
