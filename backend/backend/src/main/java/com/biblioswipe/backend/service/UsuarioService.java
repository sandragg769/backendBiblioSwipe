package com.biblioswipe.backend.service;

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

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public UsuarioService(UsuarioRepository usuarioRepository,
                          CategoriaRepository categoriaRepository,
                          PerfilRepository perfilRepository,
                          BibliotecaRepository bibliotecaRepository) {
        this.usuarioRepository = usuarioRepository;
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
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // busca un usuario por id
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    // crea un usuario
    public Usuario createUsuario(Usuario usuario) {
        // cuando se crea un usuario, también se le crea perfil y biblioteca vacíos
        Usuario nuevo = usuarioRepository.save(usuario);
        Perfil perfil = new Perfil();
        perfil.setUsuario(nuevo);
        perfilRepository.save(perfil);

        Biblioteca biblioteca = new Biblioteca(nuevo);
        bibliotecaRepository.save(biblioteca);

        return nuevo;
    }

    // NO SE SABE SI SE IMPLEMENTARÁ AHORA O A FUTURO ??????
    // actualiza un usuario
    public Usuario updateUsuario(Long id, Usuario actualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existente.setEmail(actualizado.getEmail());
        existente.setPassword(actualizado.getPassword());
        return usuarioRepository.save(existente);
    }

    // borra usuario
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // METODOS LÓGICA DE NEGOCIO
    // agregar un usuario a lista de usuarios favoritos de un usuario
    public Usuario agregarFavorito(Long usuarioId, Long favoritoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Usuario favorito = usuarioRepository.findById(favoritoId)
                .orElseThrow(() -> new RuntimeException("Usuario favorito no encontrado"));

        if (usuario.getUsuariosFavoritos().contains(favorito)) {
            throw new RuntimeException("El usuario ya está en favoritos");
        }

        usuario.getUsuariosFavoritos().add(favorito);
        return usuarioRepository.save(usuario);
    }

    // devuelve lista de usuarios favoritos de un usuario (like)
    public Set<Usuario> getFavoritos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getUsuariosFavoritos();
    }

    // agrega categoría a un usuario
    public Usuario agregarCategoria(Long usuarioId, Long categoriaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        usuario.getCategorias().add(categoria);
        return usuarioRepository.save(usuario);
    }

    // eliminar categoría
    public Usuario eliminarCategoria(Long usuarioId, Long categoriaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        usuario.getCategorias().remove(categoria);
        return usuarioRepository.save(usuario);
    }

    // enlaza usuario y perfil
    public Usuario asignarPerfil(Long usuarioId, Perfil perfil) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        perfil.setUsuario(usuario);
        perfilRepository.save(perfil);
        usuario.setPerfil(perfil);

        return usuarioRepository.save(usuario);
    }

    // obtiene el perfil (los datos) de un usuario
    public Perfil getPerfil(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getPerfil();
    }

    // registro y login
    public String registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        usuarioRepository.save(usuario);
        return "Usuario registrado correctamente";
    }

    public boolean login(String email, String password) {
        Optional<Usuario> userDb = usuarioRepository.findByEmail(email);
        return userDb.isPresent() && userDb.get().getPassword().equals(password);
    }
}
