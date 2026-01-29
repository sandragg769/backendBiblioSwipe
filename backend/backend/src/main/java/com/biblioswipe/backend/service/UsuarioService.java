package com.biblioswipe.backend.service;

import com.biblioswipe.backend.dto.*;
import com.biblioswipe.backend.mapper.BibliotecaMapper;
import com.biblioswipe.backend.mapper.PerfilMapper;
import com.biblioswipe.backend.mapper.UsuarioMapper;
import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.model.Usuario;
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
    private final UsuarioMapper usuarioMapper;
    private final PerfilMapper perfilMapper;
    private final BibliotecaMapper bibliotecaMapper;
    private final PerfilRepository perfilRepository;

    @Autowired
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            PerfilMapper perfilMapper,
            BibliotecaMapper bibliotecaMapper,
            PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.perfilMapper = perfilMapper;
        this.bibliotecaMapper = bibliotecaMapper;
        this.perfilRepository = perfilRepository;
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
                        "Usuario no encontrado"));
    }

    // crea un usuario, perfil y biblioteca vacía, (registro)
    // BIEN
    @Transactional
    public UsuarioDTO agregarUsuario(UsuarioRegisterDTO dto) {
        // 1️⃣ Verificar si el email ya existe
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ya registrado");
        }

        // 2️⃣ Crear usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); // Nota: usar BCrypt en producción

        // 3️⃣ Crear perfil y asignarlo al usuario
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario); // Relación bidireccional
        usuario.setPerfil(perfil);

        // 4️⃣ Crear biblioteca y asignarla al usuario
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setUsuario(usuario); // Relación bidireccional
        usuario.setBiblioteca(biblioteca);

        // 5️⃣ Guardar usuario (cascade guardará perfil y biblioteca)
        Usuario guardado = usuarioRepository.save(usuario);

        // 6️⃣ Devolver DTO
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
                    "No puedes agregarte a ti mismo a favoritos");
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
                        "Credenciales incorrectas"));

        // Nota: en producción usar BCrypt
        if (!usuario.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas");
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
                        contarCategoria(u1.getBiblioteca(), nombreCategoria)))
                .map(usuarioMapper::toDTO)
                .toList();
    }
public List<UsuarioSwipeDTO> getFavoritosParaSwipe(Long usuarioId) {
    Usuario usuario = getUsuarioEntity(usuarioId);
    return usuario.getUsuariosFavoritos().stream()
            .map(fav -> new UsuarioSwipeDTO(
                    fav.getUsuarioId(), // 🎯 Si da error, prueba con fav.id si es público o revisa el getter
                    fav.getPerfil().getNombre(),
                    fav.getPerfil().getCiudad(),
                    fav.getPerfil().getFotoPerfil()
            ))
            .toList();
}

    private long contarCategoria(Biblioteca biblioteca, String nombreCategoria) {
        return Stream.of(
                biblioteca.getLibrosLeidos(),
                biblioteca.getLibrosRecomendados(),
                biblioteca.getLibrosFuturasLecturas())
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

    // Añadir en UsuarioService.java
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }
/**
 *     public List<NotificacionesFavoritosDTO> obtenerNotificacionesDeFavoritos(Long id) {
    // Por ahora, devolvemos una lista de prueba para que veas que funciona la pestaña
    return List.of(
        new NotificacionesFavoritosDTO(1L, "Emi", "ha actualizado su biblioteca", "Hoy, 10:30"),
        new NotificacionesFavoritosDTO(2L, "Sandra", "recomienda un nuevo libro", "Ayer, 18:45")
    );
}
 */

}
