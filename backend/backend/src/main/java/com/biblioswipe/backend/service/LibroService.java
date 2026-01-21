package com.biblioswipe.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.biblioswipe.backend.dto.LibroCreateDTO;
import com.biblioswipe.backend.dto.LibroDTO;
import com.biblioswipe.backend.mapper.LibroMapper;
import com.biblioswipe.backend.model.Categoria;
import com.biblioswipe.backend.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.repository.LibroRepository;
import org.springframework.web.server.ResponseStatusException;

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
    // BIEN
    public List<LibroDTO> getAllLibros() {
        List<Libro> libros = libroRepository.findAll();
        return libros.stream()
                .map(libroMapper::toDTO)
                .collect(Collectors.toList());
    }

    // obtener un libro por id
    // BIEN
    public LibroDTO getLibroById(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Libro con ID " + id + " no encontrado"
                ));
        return libroMapper.toDTO(libro);
    }

    // crear un libro
    // no usar el save solo
    // BIEN
    @Transactional
    public LibroDTO crearLibro(LibroCreateDTO dto) {
        // 1. Verificamos si ya existe para no duplicar
        if (libroRepository.existsByTituloAndAutor(dto.getTitulo(), dto.getAutor())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este libro ya existe");
        }

        // 2. Buscamos la categoría (el front envía el ID)
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));

        // 3. Creamos la entidad
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setPortada(dto.getPortada());
        libro.setCategoria(categoria);

        // 4. Guardamos y devolvemos el DTO completo (el que sí tiene ID)
        return libroMapper.toDTO(libroRepository.save(libro));
    }

    // METODOS DE LÓGICA DE NEGOCIOS
    // encontrar libros por categoría
    // BIEN LOS TRES
    public List<LibroDTO> getLibrosByCategoria(Long categoriaId) {
        return libroRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(libroMapper::toDTO)
                .collect(Collectors.toList());
    }

    // buscar libros por autor
    public List<LibroDTO> buscarPorAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor)
                .stream()
                .map(libroMapper::toDTO)
                .toList();
    }

    // buscar libros por título
    public List<LibroDTO> buscarPorLibros(String query) {
        // Asumiendo que creas este metodo en el repository
        return libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(query, query)
                .stream()
                .map(libroMapper::toDTO)
                .collect(Collectors.toList());
    }

    // METODO AUXILIAR
    public Libro getLibroEntity(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Libro no encontrado"
                ));
    }

}
