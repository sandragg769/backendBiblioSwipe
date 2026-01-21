package com.biblioswipe.backend.service;

import com.biblioswipe.backend.dto.*;
import com.biblioswipe.backend.mapper.BibliotecaMapper;
import com.biblioswipe.backend.mapper.PerfilMapper;
import com.biblioswipe.backend.mapper.UsuarioMapper;
import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Categoria;
import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.model.Usuario;
import com.biblioswipe.backend.repository.BibliotecaRepository;
import com.biblioswipe.backend.repository.CategoriaRepository;
import com.biblioswipe.backend.repository.PerfilRepository;
import com.biblioswipe.backend.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

//service es la lógica del negocio
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilService perfilService;
    private final BibliotecaService bibliotecaService;
    private final UsuarioMapper usuarioMapper;
    private final PerfilMapper perfilMapper;
    private final BibliotecaMapper bibliotecaMapper;


    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PerfilService perfilService,
            BibliotecaService bibliotecaService,
            UsuarioMapper usuarioMapper,
            PerfilMapper perfilMapper,
            BibliotecaMapper bibliotecaMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.perfilService = perfilService;
        this.bibliotecaService = bibliotecaService;
        this.usuarioMapper = usuarioMapper;
        this.perfilMapper = perfilMapper;
        this.bibliotecaMapper = bibliotecaMapper;
    }

    // METODOS CRUD
    // busca un usuario por id
    // BIEN
    public UsuarioDTO getUsuario(Long id) {
        return usuarioMapper.toDTO(getUsuarioEntity(id));
    }

    public Usuario getUsuarioEntity(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));
    }

    // crea un usuario, perfil y biblioteca vacía, (registro)
    // BIEN
    public UsuarioDTO register(UsuarioRegisterDTO dto) {

        usuarioRepository.findByEmail(dto.getEmail())
                .ifPresent(u -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "El email ya está registrado"
                    );
                });

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        Usuario guardado = usuarioRepository.save(usuario);

        Perfil perfil = perfilService.crearPerfilInicial(guardado);
        Biblioteca biblioteca = bibliotecaService.crearBibliotecaInicial(guardado);

        guardado.setPerfil(perfil);
        guardado.setBiblioteca(biblioteca);

        usuarioRepository.save(guardado);

        return usuarioMapper.toDTO(guardado);
    }

    // METODOS LÓGICA DE NEGOCIO
    // agregar un usuario a lista de usuarios favoritos de un usuario
    // BIEN
    @Transactional
    public void agregarFavorito(Long usuarioId, Long favoritoId) {

        if (usuarioId.equals(favoritoId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No puedes agregarte a ti mismo a favoritos"
            );
        }

        Usuario usuario = getUsuarioEntity(usuarioId);
        Usuario favorito = getUsuarioEntity(favoritoId);

        usuario.getUsuariosFavoritos().add(favorito);
    }

    // devuelve lista de usuarios favoritos de un usuario (like)
    // BIEN
    public List<UsuarioDTO> getFavoritos(Long usuarioId) {
        Usuario usuario = getUsuarioEntity(usuarioId);
        return usuario.getUsuariosFavoritos()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    // login
    // BIEN
    public UsuarioDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Credenciales incorrectas"
                ));

        // Nota: en producción usar BCrypt
        if (!usuario.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas"
            );
        }

        return usuarioMapper.toDTO(usuario);
    }


    // FILTRADO CATEGORÍA
    public List<UsuarioDTO> buscarUsuariosPorCategoria(String nombreCategoria) {

        return usuarioRepository.findAll().stream()
                .filter(u -> u.getBiblioteca() != null)
                .filter(u -> contarCategoria(u.getBiblioteca(), nombreCategoria) > 0)
                .sorted((u1, u2) -> Long.compare(
                        contarCategoria(u2.getBiblioteca(), nombreCategoria),
                        contarCategoria(u1.getBiblioteca(), nombreCategoria)
                ))
                .map(usuarioMapper::toDTO)
                .toList();
    }

    private long contarCategoria(Biblioteca biblioteca, String nombreCategoria) {
        return Stream.of(
                        biblioteca.getLibrosLeidos(),
                        biblioteca.getLibrosRecomendados(),
                        biblioteca.getLibrosFuturasLecturas()
                )
                .flatMap(Set::stream)
                .filter(l -> l.getCategoria() != null)
                .filter(l -> l.getCategoria().getNombre().equalsIgnoreCase(nombreCategoria))
                .count();
    }


    // otros getters
    public PerfilDTO getPerfil(Long usuarioId) {
        Usuario usuario = getUsuarioEntity(usuarioId);
        return perfilMapper.toDTO(usuario.getPerfil());
    }

    public BibliotecaDTO getBiblioteca(Long usuarioId) {
        Usuario usuario = getUsuarioEntity(usuarioId);
        return bibliotecaMapper.toDTO(usuario.getBiblioteca());
    }

}
