package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Optional;

import com.biblioswipe.backend.model.Categoria;
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
    public Libro getLibroById(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    // crear un libro
    // no usar el save solo
    public Libro createLibro(String titulo, String autor, String portada, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setPortada(portada);
        libro.setCategoria(categoria);

        return libroRepository.save(libro);
    }

    // actualizar un libro concreto (id)
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
    public Libro createLibro(String titulo, String autor, String portada, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setPortada(portada);
        libro.setCategoria(categoria);

        return libroRepository.save(libro);
    }

    // eliminar un libro concreto
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }


    // METODOS DE LÓGICA DE NEGOCIOS
    // encontrar libros por categoría
    public List<Libro> buscarPorCategoria(String nombreCategoria) {
        return libroRepository.findByCategoria_NombreIgnoreCase(nombreCategoria);
    }

    // Buscar libros por autor
    public List<Libro> buscarPorAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor);
    }

    // Buscar libros por título
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
