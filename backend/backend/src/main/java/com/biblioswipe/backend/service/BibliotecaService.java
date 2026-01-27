package com.biblioswipe.backend.service;

import com.biblioswipe.backend.dto.BibliotecaDTO;
import com.biblioswipe.backend.mapper.BibliotecaMapper;
import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.model.Usuario;
import com.biblioswipe.backend.repository.BibliotecaRepository;
import com.biblioswipe.backend.repository.LibroRepository;
import com.biblioswipe.backend.repository.UsuarioRepository;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 🎯 IMPORTANTE: No olvides este import
import org.springframework.web.server.ResponseStatusException;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final BibliotecaMapper bibliotecaMapper;

    @Autowired
    public BibliotecaService(
            BibliotecaRepository bibliotecaRepository,
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            BibliotecaMapper bibliotecaMapper) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.bibliotecaMapper = bibliotecaMapper;
    }

    // --- LECTURA ---
    @Transactional(readOnly = true)
    public BibliotecaDTO getBibliotecaByUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (usuario.getBiblioteca() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no tiene biblioteca");
        }
        return bibliotecaMapper.toDTO(usuario.getBiblioteca());
    }

    @Transactional
    public Biblioteca crearBibliotecaInicial(Usuario usuario) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setUsuario(usuario);
        return bibliotecaRepository.save(biblioteca);
    }

    @Transactional
    public BibliotecaDTO agregarLibroAFuturas(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);

        // Inicializar colección si es proxy
        if (b.getLibrosFuturasLecturas() == null) {
            b.setLibrosFuturasLecturas(new HashSet<>());
        }

        // Agregar el libro
        b.getLibrosFuturasLecturas().add(getLibro(libroId));

        // Guardar la biblioteca, Hibernate actualizará la tabla intermedia
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    @Transactional
    public BibliotecaDTO agregarLibroALeidos(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosLeidos().add(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    @Transactional
    public BibliotecaDTO agregarLibroARecomendados(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosRecomendados().add(getLibro(libroId)); // Esto fallaba sin @Transactional
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    // --- ELIMINAR LIBROS (ESCRITURA) ---
    @Transactional
    public BibliotecaDTO eliminarLibroDeFuturas(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosFuturasLecturas().remove(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    @Transactional
    public BibliotecaDTO eliminarLibroDeLeidos(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosLeidos().remove(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    @Transactional
    public BibliotecaDTO eliminarLibroDeRecomendados(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosRecomendados().remove(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    // --- AUXILIARES ---
    private Libro getLibro(Long libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
    }

    private Biblioteca getBibliotecaEntity(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        if (usuario.getBiblioteca() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no tiene biblioteca");
        }
        return usuario.getBiblioteca();
    }
}