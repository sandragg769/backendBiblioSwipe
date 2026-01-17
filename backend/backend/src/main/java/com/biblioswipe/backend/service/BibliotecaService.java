package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import com.biblioswipe.backend.dto.BibliotecaDTO;
import com.biblioswipe.backend.dto.LibroCreateDTO;
import com.biblioswipe.backend.dto.LibroDTO;
import com.biblioswipe.backend.mapper.BibliotecaMapper;
import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.model.Usuario;
import com.biblioswipe.backend.repository.BibliotecaRepository;
import com.biblioswipe.backend.repository.LibroRepository;
import com.biblioswipe.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class BibliotecaService {
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final BibliotecaMapper bibliotecaMapper;

    public BibliotecaService(UsuarioRepository usuarioRepository,
                             LibroRepository libroRepository,
                             BibliotecaMapper bibliotecaMapper) {
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.bibliotecaMapper = bibliotecaMapper;
    }

    // METODOS CRUD
    // no hace falta el getAll, ni un create (se crea al crear usuario),

    // obtener una biblioteca por id
    public BibliotecaDTO getBibliotecaDeUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return bibliotecaMapper.toDTO(usuario.getBiblioteca());
    }


    // METODOS DE LÓGICA DE NEGOCIO
    // añadir libro a biblioteca
    public void agregarLibroARecomendados(Long usuarioId, Long libroId) {
        Biblioteca biblioteca = getBiblioteca(usuarioId);
        Libro libro = getLibro(libroId);

        biblioteca.getLibrosRecomendados().add(libro);
    }

    public void agregarLibroALeidos(Long usuarioId, Long libroId) {
        Biblioteca biblioteca = getBiblioteca(usuarioId);
        Libro libro = getLibro(libroId);

        biblioteca.getLibrosLeidos().add(libro);
    }

    public void agregarLibroAFuturas(Long usuarioId, Long libroId) {
        Biblioteca biblioteca = getBiblioteca(usuarioId);
        Libro libro = getLibro(libroId);

        biblioteca.getLibrosFuturasLecturas().add(libro);
    }


    //util para el controller aparte de los getters propios del model
    // obtener libros de biblioteca por clasificación
    public Set<LibroDTO> getLibrosRecomendados(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId)
                .getLibrosRecomendados()
                .stream()
                .map(libroMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<Libro> getLibrosLeidos(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId).getLibrosLeidos();
    }

    public Set<Libro> getFuturasLecturas(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId).getLibrosFuturasLecturas();
    }


    // eliminar libros de biblioteca
    public BibliotecaDTO eliminarLibroDeFuturas(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosFuturasLecturas().remove(getLibroById(libroId));
        return toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO eliminarLibroDeRecomendados(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosRecomendados().remove(getLibroById(libroId));
        return toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO eliminarLibroDeLeidos(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosLeidos().remove(getLibroById(libroId));
        return toDTO(bibliotecaRepository.save(b));
    }


    // METODO AUXILIAR
    private Libro getLibroById(Long libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    private Biblioteca getBibliotecaById(Long id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
    }

    private Biblioteca getBiblioteca(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getBiblioteca();
    }

    private Libro getLibro(Long libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    //guardar cambios biblioteca (NO HACE FALTA SEGÚN GTP, SE HACE CON EL ACTUALIZAR) ??????
}

