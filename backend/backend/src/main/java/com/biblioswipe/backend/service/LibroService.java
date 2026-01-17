package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Optional;

import com.biblioswipe.backend.dto.LibroCreateDTO;
import com.biblioswipe.backend.dto.LibroDTO;
import com.biblioswipe.backend.mapper.LibroMapper;
import com.biblioswipe.backend.model.Categoria;
import com.biblioswipe.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final CategoriaRepository categoriaRepository;
    private final LibroMapper libroMapper;


    public LibroService(LibroRepository libroRepository,
                        CategoriaRepository categoriaRepository,
                        LibroMapper libroMapper) {
        this.libroRepository = libroRepository;
        this.categoriaRepository = categoriaRepository;
        this.libroMapper = libroMapper;
    }

    // METODOS CRUD
    // obtener todos los libros
    public List<LibroDTO> getAllLibros() {
        return libroRepository.findAll()
                .stream()
                .map(libroMapper::toDTO)
                .toList();
    }

    // obtener un libro por id
    public LibroDTO getLibroById(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return libroMapper.toDTO(libro);
    }

    // crear un libro
    // no usar el save solo
    public LibroDTO createLibro(LibroCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));


        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setPortada(dto.getPortada());
        libro.setCategoria(categoria);

        return libroMapper.toDTO(libroRepository.save(libro));
    }

    // eliminar un libro concreto
    // REALMENTE NO TENEMOS ESTA IDEA PARA LA APP, FUTURA IMPLEMENTACIÓN ???????
    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }


    // METODOS DE LÓGICA DE NEGOCIOS
    // encontrar libros por categoría
    public List<LibroDTO> buscarPorCategoria(String nombreCategoria) {
        return libroRepository.findByCategoria_NombreIgnoreCase(nombreCategoria)
                .stream()
                .map(libroMapper::toDTO)
                .toList();
    }

    // Buscar libros por autor
    public List<LibroDTO> buscarPorAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor)
                .stream()
                .map(libroMapper::toDTO)
                .toList();
    }

    // Buscar libros por título
    public List<LibroDTO> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(libroMapper::toDTO)
                .toList();
    }

}
