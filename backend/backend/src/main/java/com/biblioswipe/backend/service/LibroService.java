package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // METODOS CRUD
    // obtener todos los libros
    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    // obtener un libro por id
    public Optional<Libro> getLibroById(Long id) {
        return libroRepository.findById(id);
    }

    // crear un libro
    public Libro createLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    //REALMENTE NO SE PUEDE ACTUALIZAR UN LIBRO EN NUESTRA APP (FUTURA IMPLEMENTACIÓN) ?????
    // actualizar un libro concreto (id)
    public Libro updateLibro(Long id, Libro actualizado) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        libro.setTitulo(actualizado.getTitulo());
        libro.setAutor(actualizado.getAutor());
        libro.setPortada(actualizado.getPortada());
        libro.setCategoria(actualizado.getCategoria());
        return libroRepository.save(libro);
    }

    // eliminar un libro concreto
    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }

    // METODOS DE LÓGICA DE NEGOCIOS
    // encontrar libros por categoría
    public List<Libro> findByCategoria(String categoria) {
        return libroRepository.findByCategoria_NombreIgnoreCase(categoria);
    }

    // Buscar libros por autor
    public List<Libro> findByAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor);
    }

    // Buscar libros por título
    public List<Libro> findByTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
