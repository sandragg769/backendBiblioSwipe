package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.PerfilConUsuarioDTO;
import com.biblioswipe.backend.dto.PerfilUpdateDTO;
import com.biblioswipe.backend.model.Perfil;

public class PerfilMapper {

    public static PerfilConUsuarioDTO toDTO(Perfil perfil) {
        return new PerfilConUsuarioDTO(
                perfil.getPerfil_id(),
                perfil.getNombre(),
                perfil.getApellidos(),
                perfil.getFechaNacimiento(),
                perfil.getCiudad(),
                perfil.getFotoPerfil(),
                perfil.getUsuario() != null ? UsuarioMapper.toDTO(perfil.getUsuario()) : null
        );
    }

    public static Perfil toEntity(PerfilUpdateDTO dto, Perfil perfilExistente) {
        perfilExistente.setNombre(dto.getNombre());
        perfilExistente.setApellidos(dto.getApellidos());
        perfilExistente.setFechaNacimiento(dto.getFechaNacimiento());
        perfilExistente.setCiudad(dto.getCiudad());
        perfilExistente.setFotoPerfil(dto.getFotoPerfil());
        return perfilExistente;
    }
}

