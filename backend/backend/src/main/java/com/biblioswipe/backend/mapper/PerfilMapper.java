package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.PerfilUpdateDTO;
import com.biblioswipe.backend.model.Perfil;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {

    private final UsuarioMapper usuarioMapper;

    public PerfilMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public PerfilDTO toDTO(Perfil perfil) {
        return new PerfilDTO(
                perfil.getPerfil_id(),
                perfil.getNombre(),
                perfil.getApellidos(),
                perfil.getFechaNacimiento(),
                perfil.getCiudad(),
                perfil.getFotoPerfil(),
                perfil.getUsuario() != null
                        ? usuarioMapper.toDTO(perfil.getUsuario())
                        : null
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


