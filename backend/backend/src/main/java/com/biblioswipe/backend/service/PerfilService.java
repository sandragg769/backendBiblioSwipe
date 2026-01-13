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
    public Perfil getPerfilById(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    // crear un perfil (a la hora de la creación de la cuenta)
    // ??????? REALMENTE HACE FALTA ???
    public Perfil createPerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    // actualizar perfil concreto
    public Perfil updatePerfil(Long id, Perfil actualizado) {
        Perfil perfil = getPerfilById(id);

        perfil.setNombre(actualizado.getNombre());
        perfil.setApellidos(actualizado.getApellidos());
        perfil.setFechaNacimiento(actualizado.getFechaNacimiento());
        perfil.setCiudad(actualizado.getCiudad());
        perfil.setFotoPerfil(actualizado.getFotoPerfil());

        return perfilRepository.save(perfil);
    }

    // eliminar perfil concreto
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
    public void deletePerfil(Long id) {
        perfilRepository.deleteById(id);
    }


    // METODOS DE LÓGICA DE NEGOCIO
    // localizar perfiles con la misma ciudad
    public List<Perfil> findByCiudad(String ciudad) {
        return perfilRepository.findByCiudadIgnoreCase(ciudad);
    }

}
