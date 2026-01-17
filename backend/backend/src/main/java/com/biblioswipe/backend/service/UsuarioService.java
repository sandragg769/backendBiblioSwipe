package com.biblioswipe.backend.service;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.UsuarioDTO;
import com.biblioswipe.backend.dto.UsuarioMatchDTO;
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

    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioMapper usuarioMapper,
                          CategoriaRepository categoriaRepository,
                          PerfilRepository perfilRepository,
                          BibliotecaRepository bibliotecaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.categoriaRepository = categoriaRepository;
        this.perfilRepository = perfilRepository;
        this.bibliotecaRepository = bibliotecaRepository;
    }

    // METODOS CRUD
    // obtiene todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // busca un usuario por email
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // busca un usuario por id
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // crea un usuario, perfil y biblioteca vacía
    public Usuario createUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // guardar usuario
        Usuario nuevo = usuarioRepository.save(usuario);

        // crear perfil vacío
        Perfil perfil = new Perfil();
        perfil.setUsuario(nuevo);
        perfilRepository.save(perfil);

        // crear biblioteca vacía
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setUsuario(nuevo);
        bibliotecaRepository.save(biblioteca);

        return nuevo;
    }

    // actualiza un usuario
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
    public Usuario updateUsuario(Long id, Usuario actualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existente.setEmail(actualizado.getEmail());
        existente.setPassword(actualizado.getPassword());
        return usuarioRepository.save(existente);
    }

    // borra usuario
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }


    // METODOS LÓGICA DE NEGOCIO
    // agregar un usuario a lista de usuarios favoritos de un usuario
    public void agregarFavorito(Long usuarioId, Long favoritoId) {
        Usuario usuario = getUsuarioById(usuarioId);
        Usuario favorito = getUsuarioById(favoritoId);

        if (usuario.getUsuariosFavoritos().contains(favorito)) {
            return;
        }

        usuario.getUsuariosFavoritos().add(favorito);
        usuarioRepository.save(usuario);
    }

    // devuelve lista de usuarios favoritos de un usuario (like)
    public Set<Usuario> getFavoritos(Long usuarioId) {
        return getUsuarioById(usuarioId).getUsuariosFavoritos();
    }

    // agrega categoría a un usuario
    public void agregarCategoria(Long usuarioId, Long categoriaId) {
        Usuario usuario = getUsuarioById(usuarioId);
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        usuario.getCategorias().add(categoria);
        usuarioRepository.save(usuario);
    }

    // eliminar categoría
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENACIÓN ???????
    public Usuario eliminarCategoria(Long usuarioId, Long categoriaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        usuario.getCategorias().remove(categoria);
        return usuarioRepository.save(usuario);
    }

    // obtiene el perfil (los datos) de un usuario
    public Perfil getPerfilDeUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getPerfil();
    }

    public Biblioteca getBibliotecaDeUsuario(Long usuarioId) {
        return getUsuarioById(usuarioId).getBiblioteca();
    }

    // registro
    public String registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        createUsuario(usuario);
        return "Usuario registrado correctamente";
    }

    // login
    public boolean login(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .map(u -> u.getPassword().equals(password))
                .orElse(false);
    }


    // FILTRADO CATEGORÍA
    public List<UsuarioMatchDTO> buscarUsuariosPorCategoria(String categoria) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuario -> {
                    Biblioteca b = usuario.getBiblioteca();

                    long count = Stream.concat(
                                    b.getLibrosLeidos().stream(),
                                    b.getLibrosRecomendados().stream()
                            )
                            .filter(libro ->
                                    libro.getCategoria() != null &&
                                            libro.getCategoria().getNombre().equalsIgnoreCase(categoria)
                            )
                            .count();

                    return new UsuarioMatchDTO(usuario, count);
                })
                .filter(dto -> dto.getCoincidencias() > 0)
                .sorted(Comparator.comparingLong(UsuarioMatchDTO::getCoincidencias).reversed())
                .toList();
    }

    // DTOS (LO QUE USA EL FRONT)

    public List<UsuarioDTO> getAllUsuariosDTO() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    public Optional<UsuarioDTO> getUsuarioDTOById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO);
    }

    public List<UsuarioDTO> getFavoritosDTO(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuario.getUsuariosFavoritos()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }


}
