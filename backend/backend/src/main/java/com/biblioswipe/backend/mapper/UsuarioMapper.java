package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.UsuarioDTO;
import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {

        Perfil perfil = usuario.getPerfil();
        PerfilDTO perfilDTO = null;

        if (perfil != null) {
            perfilDTO = new PerfilDTO(
                    perfil.getPerfil_id(),
                    perfil.getNombre(),
                    perfil.getApellidos(),
                    perfil.getFechaNacimiento(),
                    perfil.getCiudad(),
                    perfil.getFotoPerfil(),
                    null
            );
        }

        return new UsuarioDTO(
                usuario.getUsuario_id(),
                usuario.getEmail(),
                perfilDTO
        );
    }
}
