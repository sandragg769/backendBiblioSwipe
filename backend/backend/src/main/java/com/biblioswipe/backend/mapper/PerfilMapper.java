package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.PerfilUpdateDTO;
import com.biblioswipe.backend.model.Perfil;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {
    public PerfilDTO toDTO(Perfil perfil) {
        if (perfil == null) return null;
        return new PerfilDTO(
                perfil.getPerfil_id(),
                perfil.getNombre(),
                perfil.getApellidos(),
                perfil.getFechaNacimiento(), // Asegúrate que el DTO acepte LocalDate o conviértelo a String
                perfil.getCiudad(),
                perfil.getFotoPerfil()
                //perfil.getUsuario() != null ? perfil.getUsuario().getUsuario_id() : null // Solo el ID
        );
    }

    public void updateEntity(Perfil perfil, PerfilUpdateDTO dto) {
        perfil.setNombre(dto.getNombre());
        perfil.setApellidos(dto.getApellidos());
        perfil.setFechaNacimiento(dto.getFechaNacimiento());
        perfil.setCiudad(dto.getCiudad());
        perfil.setFotoPerfil(dto.getFotoPerfil());
    }
}


