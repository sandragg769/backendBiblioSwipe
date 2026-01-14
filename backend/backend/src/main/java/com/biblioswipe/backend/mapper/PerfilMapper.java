package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.PerfilConUsuarioDTO;
import com.biblioswipe.backend.dto.UsuarioDTO;
import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.model.Usuario;

public class PerfilMapper {

    public static PerfilConUsuarioDTO toDTO(Perfil perfil) {
        Usuario usuario = perfil.getUsuario();

        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getUsuario_id(),
                usuario.getEmail()
        );

        return new PerfilConUsuarioDTO(
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
