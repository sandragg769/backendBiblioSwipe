package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Optional;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.PerfilUpdateDTO;
import com.biblioswipe.backend.mapper.PerfilMapper;
import com.biblioswipe.backend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;

import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.repository.PerfilRepository;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    public PerfilService(PerfilRepository perfilRepository,
                         PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    // METODOS CRUD
    // obtener todos los perfiles
    public List<PerfilDTO> getAllPerfiles() {
        return perfilRepository.findAll()
                .stream()
                .map(perfilMapper::toDTO)
                .toList();
    }

    // obtener un perfil en concreto por id
    public PerfilDTO getPerfilById(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return perfilMapper.toDTO(perfil);
    }

    // crear perfil (para el create usuario)
    public Perfil crearPerfilInicial(Usuario usuario) {

        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);

        return perfilRepository.save(perfil);
    }

    // actualizar perfil concreto
    public PerfilDTO updatePerfil(Long id, PerfilUpdateDTO dto) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        perfilMapper.updateEntity(perfil, dto);

        return perfilMapper.toDTO(
                perfilRepository.save(perfil)
        );
    }


    // METODOS DE LÓGICA DE NEGOCIO
    // localizar perfiles con la misma ciudad
    public List<PerfilDTO> findByCiudad(String ciudad) {
        return perfilRepository.findByCiudadIgnoreCase(ciudad)
                .stream()
                .map(perfilMapper::toDTO)
                .toList();
    }
}


