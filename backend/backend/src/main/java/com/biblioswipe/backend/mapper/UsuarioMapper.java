package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.UsuarioDTO;
import com.biblioswipe.backend.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        return new UsuarioDTO(
                usuario.getUsuario_id(),
                usuario.getEmail()
        );
    }
}
