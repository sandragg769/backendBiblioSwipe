package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Optional;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.PerfilUpdateDTO;
import com.biblioswipe.backend.mapper.PerfilMapper;
import com.biblioswipe.backend.model.Usuario;
import com.biblioswipe.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.repository.PerfilRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PerfilService {
    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfilMapper perfilMapper;

    public PerfilService(
            PerfilRepository perfilRepository,
            UsuarioRepository usuarioRepository,
            PerfilMapper perfilMapper
    ) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
        this.perfilMapper = perfilMapper;
    }

    // METODOS CRUD
    // obtener un perfil en concreto por id
    // BIEN
    public PerfilDTO getPerfilByUsuario(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        if (usuario.getPerfil() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no tiene perfil"
            );
        }

        return perfilMapper.toDTO(usuario.getPerfil());
    }

    // crear perfil (para el create usuario)
    // no dto ya que trabaja dentro de otro servicio este metodo
    // BIEN
    public Perfil crearPerfilInicial(Usuario usuario) {

        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);

        // valores por defecto
        perfil.setNombre("");
        perfil.setApellidos("");
        perfil.setCiudad("");
        perfil.setFotoPerfil(null);
        perfil.setFechaNacimiento(null);

        return perfilRepository.save(perfil);
    }

    // actualizar perfil concreto
    // BIEN
    public PerfilDTO actualizarPerfil(Long usuarioId, PerfilUpdateDTO dto) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        Perfil perfil = usuario.getPerfil();

        if (perfil == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no tiene perfil"
            );
        }

        perfil.setNombre(dto.getNombre());
        perfil.setApellidos(dto.getApellidos());
        perfil.setCiudad(dto.getCiudad());
        perfil.setFechaNacimiento(dto.getFechaNacimiento());
        perfil.setFotoPerfil(dto.getFotoPerfil());

        return perfilMapper.toDTO(perfilRepository.save(perfil));
    }


    // METODOS DE LÓGICA DE NEGOCIO
    // localizar perfiles con la misma ciudad
    // NO NECESARIO SE SUPONE !!!!!
    public List<PerfilDTO> findByCiudad(String ciudad) {
        return perfilRepository.findByCiudadIgnoreCase(ciudad)
                .stream()
                .map(perfilMapper::toDTO)
                .toList();
    }
}


