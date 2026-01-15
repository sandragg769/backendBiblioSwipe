package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import com.biblioswipe.backend.dto.BibliotecaDTO;
import com.biblioswipe.backend.dto.LibroCreateDTO;
import com.biblioswipe.backend.dto.LibroDTO;
import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.repository.BibliotecaRepository;
import com.biblioswipe.backend.repository.LibroRepository;
import org.springframework.stereotype.Service;

@Service
public class BibliotecaService {
    private final BibliotecaRepository bibliotecaRepository;
    private final LibroRepository libroRepository;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository,
                             LibroRepository libroRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.libroRepository = libroRepository;
    }

    // METODOS CRUD
    // obtener todas las bibliotecas
    public List<Biblioteca> getAllBibliotecas() {
        return bibliotecaRepository.findAll();
    }

    // obtener una biblioteca por id
    public BibliotecaDTO getBibliotecaByIdDTO(Long bibliotecaId) {
        Biblioteca biblioteca = getBibliotecaById(bibliotecaId);

        return new BibliotecaDTO(
                biblioteca.getId(),
                toLibroCreateDTOSet(biblioteca.getLibrosRecomendados()),
                toLibroCreateDTOSet(biblioteca.getLibrosLeidos()),
                toLibroCreateDTOSet(biblioteca.getLibrosFuturasLecturas())
        );
    }

    // crear una biblioteca
    // Crear biblioteca (normalmente NO se usa directamente,
    // porque se crea automáticamente al crear un usuario)
    public Biblioteca createBiblioteca(Biblioteca biblioteca) {
        return bibliotecaRepository.save(biblioteca);
    }

    public void deleteBiblioteca(Long id) {
        bibliotecaRepository.deleteById(id);
    }

    // actualizar una biblioteca
    // dejar o quitar ???? gpt dice quitar
    public Biblioteca updateBiblioteca(Long id, Biblioteca actualizada) {
        Biblioteca existente = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
        existente.setLibrosLeidos(actualizada.getLibrosLeidos() != null ?
                actualizada.getLibrosLeidos() : existente.getLibrosLeidos());
        existente.setLibrosRecomendados(actualizada.getLibrosRecomendados() != null ?
                actualizada.getLibrosRecomendados() : existente.getLibrosRecomendados());
        existente.setFuturasLecturas(actualizada.getLibrosFuturasLecturas() != null ?
                actualizada.getLibrosFuturasLecturas() : existente.getLibrosFuturasLecturas());

        return bibliotecaRepository.save(existente);
    }


    // METODOS DE LÓGICA DE NEGOCIO
    // añadir libro a biblioteca
    public BibliotecaDTO agregarLibroARecomendados(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosRecomendados().add(getLibroById(libroId));
        return toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO agregarLibroALeidos(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosLeidos().add(getLibroById(libroId));
        return toDTO(bibliotecaRepository.save(b));
    }

    public BibliotecaDTO agregarLibroAFuturas(Long bibliotecaId, Long libroId) {
        Biblioteca b = getBibliotecaById(bibliotecaId);
        b.getLibrosFuturasLecturas().add(getLibroById(libroId));
        return toDTO(bibliotecaRepository.save(b));
    }


    //util para el controller aparte de los getters propios del model
    // obtener libros de biblioteca por clasificación
    public Set<Libro> getLibrosRecomendados(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId).getLibrosRecomendados();
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

    private BibliotecaDTO toDTO(Biblioteca b) {
        return new BibliotecaDTO(
                b.getId(),
                toLibroCreateDTOSet(b.getLibrosRecomendados()),
                toLibroCreateDTOSet(b.getLibrosLeidos()),
                toLibroCreateDTOSet(b.getLibrosFuturasLecturas())
        );
    }

    // QUITAR ????
    private Set<LibroDTO> toLibroDTOSet(Set<Libro> libros) {
        return libros.stream()
                .map(this::toLibroDTO)
                .collect(Collectors.toSet());
    }

    // QUITAR ????
    private LibroDTO toLibroDTO(Libro libro) {
        return new LibroDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getPortada(),
                libro.getCategoria() != null ? libro.getCategoria().getNombre() : null
        );
    }

    private Set<LibroCreateDTO> toLibroCreateDTOSet(Set<Libro> libros) {
        return libros.stream()
                .map(this::toLibroCreateDTO)
                .collect(Collectors.toSet());
    }

    private LibroCreateDTO toLibroCreateDTO(Libro libro) {
        return new LibroCreateDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getCategoria() != null ? libro.getCategoria().getId() : null,
                libro.getPortada()
        );
    }

    //guardar cambios biblioteca (NO HACE FALTA SEGÚN GTP, SE HACE CON EL ACTUALIZAR) ??????
}

