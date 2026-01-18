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
import org.springframework.stereotype.Service;

@Service
public class BibliotecaService {
    private final BibliotecaRepository bibliotecaRepository;
    private final LibroRepository libroRepository;
    private final BibliotecaMapper bibliotecaMapper;
    private final LibroMapper libroMapper;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository,
                             LibroRepository libroRepository,
                             BibliotecaMapper bibliotecaMapper,
                             LibroMapper libroMapper) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.libroRepository = libroRepository;
        this.bibliotecaMapper = bibliotecaMapper;
        this.libroMapper = libroMapper;
    }

    // METODOS CRUD
    // no hace falta el getAll, ni un create (se crea al crear usuario), ni el delete, ni update???

    // obtener una biblioteca por id
    public BibliotecaDTO getBibliotecaDTOById(Long bibliotecaId) {
        return bibliotecaMapper.toDTO(getBibliotecaById(bibliotecaId));
    }

    // rear biblioteca (para el create usuario)
    public Biblioteca crearBibliotecaInicial(Usuario usuario) {

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setUsuario(usuario);

        return bibliotecaRepository.save(biblioteca);
    }


    // METODOS DE LÓGICA DE NEGOCIO
    // añadir libro a biblioteca
    public void agregarLibroARecomendados(Long usuarioId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(usuarioId);
        Libro libro = getLibroById(libroId);

        biblioteca.getLibrosRecomendados().add(libro);
    }

    public void agregarLibroALeidos(Long usuarioId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(usuarioId);
        Libro libro = getLibroById(libroId);

        biblioteca.getLibrosLeidos().add(libro);
    }

    public void agregarLibroAFuturas(Long usuarioId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(usuarioId);
        Libro libro = getLibroById(libroId);

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
    }


    // eliminar libros de biblioteca
    public BibliotecaDTO eliminarLibroDeRecomendados(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosRecomendados().remove(getLibroById(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO eliminarLibroDeLeidos(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosLeidos().remove(getLibroById(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO eliminarLibroDeFuturas(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosFuturasLecturas().remove(getLibroById(libroId));
        return bibliotecaMapper.toDTO(bibliotecaRepository.save(b));
    }


    // METODO AUXILIAR
    private Biblioteca getBibliotecaById(Long id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
    }

    private Libro getLibroById(Long libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }
}

