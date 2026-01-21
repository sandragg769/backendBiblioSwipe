package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import com.biblioswipe.backend.dto.BibliotecaDTO;
import com.biblioswipe.backend.dto.LibroCreateDTO;
import com.biblioswipe.backend.dto.LibroDTO;
import com.biblioswipe.backend.mapper.BibliotecaMapper;
import com.biblioswipe.backend.mapper.LibroMapper;
import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.model.Usuario;
import com.biblioswipe.backend.repository.BibliotecaRepository;
import com.biblioswipe.backend.repository.LibroRepository;
import com.biblioswipe.backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BibliotecaService {
    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final BibliotecaMapper bibliotecaMapper;

    public BibliotecaService(
            BibliotecaRepository bibliotecaRepository,
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            BibliotecaMapper bibliotecaMapper
    ) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.bibliotecaMapper = bibliotecaMapper;
    }

    // METODOS CRUD
    // obtener una biblioteca por id
    // BIEN
    public BibliotecaDTO getBibliotecaByUsuario(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        if (usuario.getBiblioteca() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no tiene biblioteca"
            );
        }

        return bibliotecaMapper.toDTO(usuario.getBiblioteca());
    }

    // crear biblioteca (para el create usuario)
    // no dto ya que trabaja dentro de otro servicio este metodo
    // BIEN
    public Biblioteca crearBibliotecaInicial(Usuario usuario) {

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setUsuario(usuario);

        return bibliotecaRepository.save(biblioteca);
    }


    // METODOS DE LÓGICA DE NEGOCIO
    // añadir libro a biblioteca
    // BIEN
    public BibliotecaDTO agregarLibroAFuturas(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosFuturasLecturas().add(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO agregarLibroALeidos(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosLeidos().add(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO agregarLibroARecomendados(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosRecomendados().add(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    // NO NECESARIO !!!!
    //util para el controller aparte de los getters propios del model
    // obtener libros de biblioteca por clasificación
    /*public Set<LibroDTO> getLibrosRecomendados(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId)
                .getLibrosRecomendados()
                .stream()
                .map(libroMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<LibroDTO> getLibrosLeidos(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId)
                .getLibrosLeidos()
                .stream()
                .map(libroMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<LibroDTO> getFuturasLecturas(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId)
                .getLibrosFuturasLecturas()
                .stream()
                .map(libroMapper::toDTO)
                .collect(Collectors.toSet());
    }*/


    // eliminar libros de biblioteca
    // BIEN
    public BibliotecaDTO eliminarLibroDeFuturas(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosFuturasLecturas().remove(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO eliminarLibroDeLeidos(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosLeidos().remove(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO eliminarLibroDeRecomendados(Long usuarioId, Long libroId) {
        Biblioteca b = getBibliotecaEntity(usuarioId);
        b.getLibrosRecomendados().remove(getLibro(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }


    // METODO AUXILIAR
    private Libro getLibro(Long libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Libro no encontrado"
                ));
    }

    private Biblioteca getBibliotecaEntity(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        if (usuario.getBiblioteca() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no tiene biblioteca"
            );
        }

        return usuario.getBiblioteca();
    }
}

