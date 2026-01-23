package com.biblioswipe.backend.mapper;

import com.biblioswipe.backend.dto.UsuarioDTO;
import com.biblioswipe.backend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    private final PerfilMapper perfilMapper;

    @Autowired
    public UsuarioMapper(PerfilMapper perfilMapper) {
        this.perfilMapper = perfilMapper;
    }

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getUsuario_id(),
                usuario.getEmail(),
                perfilMapper.toDTO(usuario.getPerfil()) // Faltaba este parámetro
        );
    }
}
