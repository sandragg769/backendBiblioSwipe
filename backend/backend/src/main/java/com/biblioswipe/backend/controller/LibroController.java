package com.biblioswipe.backend.controller;

import java.util.List;

import com.biblioswipe.backend.dto.LibroCreateDTO;
import com.biblioswipe.backend.dto.LibroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.repository.LibroRepository;
import com.biblioswipe.backend.service.LibroService;

@RestController
@RequestMapping("/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // GET libro
    @GetMapping
    public List<LibroDTO> getAllLibros() {
        return libroService.getAllLibros();
    }

    // GET con ID libro
    @GetMapping("/{id}")
    public LibroDTO getLibroById(@PathVariable Long id) {
        return libroService.getLibroById(id);
    }

    // GET con autor libro
    // Buscar por autor
    @GetMapping("/autor/{autor}")
    public List<LibroDTO> buscarPorAutor(@PathVariable String autor) {
        return libroService.buscarPorAutor(autor);
    }

    // GET con título libro
    // Buscar por título
    @GetMapping("/titulo/{titulo}")
    public List<LibroDTO> buscarPorTitulo(@PathVariable String titulo) {
        return libroService.buscarPorTitulo(titulo);
    }

    // GET con categoria libro
    // Buscar por categoría
    @GetMapping("/categoria/{nombre}")
    public List<LibroDTO> buscarPorCategoria(@PathVariable String nombre) {
        return libroService.buscarPorCategoria(nombre);
    }

    // POST libro
    @PostMapping
    public LibroDTO createLibro(@RequestBody LibroCreateDTO dto) {
        return libroService.createLibro(dto);
    }


    // DELETE con ID libro
    @DeleteMapping("/{id}")
    public void deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
    }
}
