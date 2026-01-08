package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.UsuarioPerfilDTO;
import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.model.Usuario;

public class PerfilMapper {

    public static PerfilDTO toDTO(Perfil perfil) {
        Usuario usuario = perfil.getUsuario();

        UsuarioPerfilDTO usuarioDTO = new UsuarioPerfilDTO(
                usuario.getUsuario_id(),
                usuario.getEmail()
        );

        return new PerfilDTO(
                perfil.getPerfil_id(),
                perfil.getNombre(),
                perfil.getApellidos(),
                perfil.getFechaNacimiento(),
                perfil.getCiudad(),
                perfil.getFotoPerfil(),
                usuarioDTO
        );
    }
}
