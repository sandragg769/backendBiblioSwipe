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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    // obtiene todos los usuarios
    // NO SE NECESITA, LO DEJO POR SI ACASO
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // busca un usuario por email
    // NO SE NECESITA, LO DEJO POR SI ACASO
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // busca un usuario por id
    public UsuarioDTO getUsuarioDTO(Long id) {
        return usuarioMapper.toDTO(getUsuarioEntity(id));
    }

    public Usuario getUsuarioEntity(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // crea un usuario, perfil y biblioteca vacía, (registro)
    public UsuarioDTO register(UsuarioRegisterDTO dto) {

        usuarioRepository.findByEmail(dto.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("El email ya está registrado");
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

    // actualiza un usuario
    // NO SE NECESITA, LO DEJO POR SI ACASO
    public Usuario updateUsuario(Long id, Usuario actualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existente.setEmail(actualizado.getEmail());
        existente.setPassword(actualizado.getPassword());
        return usuarioRepository.save(existente);
    }

    // borra usuario
    // NO SE NECESITA, LO DEJO POR SI ACASO
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }


    // METODOS LÓGICA DE NEGOCIO
    // agregar un usuario a lista de usuarios favoritos de un usuario
    public void agregarFavorito(Long usuarioId, Long favoritoId) {
        Usuario usuario = getUsuarioEntity(usuarioId);
        Usuario favorito = getUsuarioEntity(favoritoId);

        if (usuario.getUsuariosFavoritos().contains(favorito)) {
            return;
        }

        usuario.getUsuariosFavoritos().add(favorito);
        usuarioRepository.save(usuario);
    }

    // devuelve lista de usuarios favoritos de un usuario (like)
    public List<UsuarioDTO> getFavoritos(Long usuarioId) {
        Usuario usuario = getUsuarioEntity(usuarioId);

        return usuario.getUsuariosFavoritos()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    // login
    public UsuarioDTO login(String email, String password) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        return usuarioMapper.toDTO(usuario);
    }


    // FILTRADO CATEGORÍA
    public List<UsuarioDTO> buscarUsuariosPorCategoria(String nombreCategoria) {

        return usuarioRepository.findAll().stream()
                .filter(u -> u.getBiblioteca() != null)
                .sorted((u1, u2) -> {
                    long c1 = contarCategoria(u1.getBiblioteca(), nombreCategoria);
                    long c2 = contarCategoria(u2.getBiblioteca(), nombreCategoria);
                    return Long.compare(c2, c1); // DESC
                })
                .filter(u -> contarCategoria(u.getBiblioteca(), nombreCategoria) > 0)
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
