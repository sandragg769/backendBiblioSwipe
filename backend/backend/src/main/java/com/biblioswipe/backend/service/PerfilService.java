package com.biblioswipe.backend.service;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.PerfilUpdateDTO;
import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.repository.PerfilRepository;
import com.biblioswipe.backend.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PerfilService(PerfilRepository perfilRepository,
                         UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene el perfil asociado a un usuario usando la Query personalizada
     */
    @Transactional(readOnly = true)
    public PerfilDTO getPerfilByUsuario(Long usuarioId) {
        // 🎯 Llama al método con @Query de tu PerfilRepository
        Perfil perfil = perfilRepository.findByUsuario_UsuarioId(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Perfil no encontrado para usuarioId: " + usuarioId)
                );

        return toDTO(perfil);
    }

    /**
     * Actualiza el perfil asociado a un usuario
     */
    @Transactional
    public PerfilDTO actualizarPerfil(Long usuarioId, PerfilUpdateDTO dto) {
        // 🎯 Llama al método con @Query para encontrar el perfil a editar
        Perfil perfil = perfilRepository.findByUsuario_UsuarioId(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Perfil no encontrado para usuarioId: " + usuarioId)
                );

        perfil.setNombre(dto.getNombre());
        perfil.setApellidos(dto.getApellidos());
        perfil.setCiudad(dto.getCiudad());
        perfil.setFechaNacimiento(dto.getFechaNacimiento());
        perfil.setFotoPerfil(dto.getFotoPerfil());

        Perfil perfilActualizado = perfilRepository.save(perfil);

        return toDTO(perfilActualizado);
    }

    /**
     * Busca perfiles por ciudad para mostrar a otros usuarios
     */
    @Transactional(readOnly = true)
    public List<PerfilDTO> findByCiudad(String ciudad) {
        // 1. Obtenemos la lista de entidades Perfil desde el repositorio
        List<Perfil> perfiles = perfilRepository.findByCiudad(ciudad);

        // 2. Convertimos cada Perfil en un PerfilDTO
        return perfiles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte entidad Perfil a DTO para enviar al Frontend de Android
     */
    private PerfilDTO toDTO(Perfil perfil) {
        return new PerfilDTO(
                perfil.getPerfil_id(),
                perfil.getNombre(),
                perfil.getApellidos(),
                perfil.getFechaNacimiento(),
                perfil.getCiudad(),
                perfil.getFotoPerfil()
        );
    }
}