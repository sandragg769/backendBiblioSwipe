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
    public Biblioteca getBibliotecaById(Long id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Biblioteca no encontrada"));
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
    public Biblioteca agregarLibroARecomendados(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(bibliotecaId);
        Libro libro = getLibroById(libroId);

        biblioteca.getLibrosRecomendados().add(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    public Biblioteca agregarLibroALeidos(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(bibliotecaId);
        Libro libro = getLibroById(libroId);

        biblioteca.getLibrosLeidos().add(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    public Biblioteca agregarLibroAFuturasLecturas(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(bibliotecaId);
        Libro libro = getLibroById(libroId);

        biblioteca.getLibrosFuturasLecturas().add(libro);
        return bibliotecaRepository.save(biblioteca);
    }


    //util para el controller aparte de los getters propios del model
    // obtener libros de biblioteca por clasificación
    public Set<Libro> getLibrosRecomendados(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId).getLibrosRecomendados();
    }

    public Set<Libro> getLibrosLeidos(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId).getLibrosLeidos();
    }

    public Set<Libro> getLibrosFuturasLecturas(Long bibliotecaId) {
        return getBibliotecaById(bibliotecaId).getLibrosFuturasLecturas();
    }


    // eliminar libros de biblioteca
    public Biblioteca eliminarLibroDeFuturasLecturas(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(bibliotecaId);
        Libro libro = getLibroById(libroId);

        biblioteca.getLibrosFuturasLecturas().remove(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    public Biblioteca eliminarLibroDeRecomendados(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(bibliotecaId);
        Libro libro = getLibroById(libroId);

        biblioteca.getLibrosRecomendados().remove(libro);
        return bibliotecaRepository.save(biblioteca);
    }

    public Biblioteca eliminarLibroDeLeidos(Long bibliotecaId, Long libroId) {
        Biblioteca biblioteca = getBibliotecaById(bibliotecaId);
        Libro libro = getLibroById(libroId);

        biblioteca.getLibrosLeidos().remove(libro);
        return bibliotecaRepository.save(biblioteca);
    }


    // METODO AUXILIAR
    private Libro getLibroById(Long libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    //guardar cambios biblioteca (NO HACE FALTA SEGÚN GTP, SE HACE CON EL ACTUALIZAR) ??????
}

