package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.repository.BibliotecaRepository;
import com.biblioswipe.backend.repository.LibroRepository;
import org.springframework.stereotype.Service;

@Service
public class BibliotecaService {
    @Autowired
    private final BibliotecaRepository bibliotecaRepository;
    @Autowired
    private LibroRepository libroRepository;

    public BibliotecaService(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    // METODOS CRUD
    // obtener todas las bibliotecas
    public List<Biblioteca> getAllBibliotecas() {
        return bibliotecaRepository.findAll();
    }

    // obtener una biblioteca por id
    public Optional<Biblioteca> getBibliotecaById(Long id) {
        return bibliotecaRepository.findById(id);
    }

    // crear una biblioteca
    public Biblioteca createBiblioteca(Biblioteca biblioteca) {
        return bibliotecaRepository.save(biblioteca);
    }

    // actualizar una biblioteca
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
    // añadir libro a recomendadas
    public Biblioteca agregarLibroARecomendados(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        biblioteca.getLibrosRecomendados().add(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    // añadir libro a leídos
    public Biblioteca agregarLibroALeidos(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        biblioteca.getLibrosLeidos().add(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    // añadir libro a futuras lecturas
    public Biblioteca agregarLibroAFuturas(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        biblioteca.getLibrosFuturasLecturas().add(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    //util para el controller aparte de los getters propios del model
    // obtener libros recomendados
    public Set<Libro> getLibrosRecomendados(Long bibliotecaId) {
        return bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"))
                .getLibrosRecomendados();
    }

    public Set<Libro> getLibrosLeidos(Long bibliotecaId) {
        return bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"))
                .getLibrosLeidos();
    }

    public Set<Libro> getFuturasLecturas(Long bibliotecaId) {
        return bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"))
                .getLibrosFuturasLecturas();
    }

    // eliminar libros de biblioteca
    public Biblioteca removeLibroDeFuturas(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        biblioteca.getLibrosFuturasLecturas().remove(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    public Biblioteca removeLibroDeRecomendados(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        biblioteca.getLibrosRecomendados().remove(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    public Biblioteca removeLibroDeLeidos(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        biblioteca.getLibrosLeidos().remove(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    //guardar cambios biblioteca (NO HACE FALTA SEGÚN GTP, SE HACE CON EL ACTUALIZAR) ??????
}

