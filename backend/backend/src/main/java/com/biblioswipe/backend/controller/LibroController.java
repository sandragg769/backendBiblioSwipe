package com.biblioswipe.backend.controller;

import java.util.List;

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
    public List<Libro> getAllLibros() {
        return libroService.getAllLibros();
    }

    // GET con ID libro
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        return libroService.getLibroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //
    // GET con autor libro
    // Buscar por autor
    @GetMapping("/autor/{nombre}")
    public List<Libro> getLibrosByAutor(@PathVariable String nombre) {
        return libroService.findByAutor(nombre);
    }

    // GET con título libro
    // Buscar por título
    @GetMapping("/titulo/{titulo}")
    public List<Libro> getLibrosByTitulo(@PathVariable String titulo) {
        return libroService.findByTitulo(titulo);
    }

    // GET con categoria libro
    // Buscar por categoría
    @GetMapping("/categoria/{nombre}")
    public List<Libro> getLibrosByCategoria(@PathVariable String nombre) {
        return libroService.findByCategoria(nombre);
    }

    // POST libro
    @PostMapping
    public Libro createLibro(@RequestBody Libro libro) {
        return libroService.createLibro(libro);
    }

    // PUT con id libro
    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        return libroService.getLibroById(id)
                .map(existing -> {
                    libro.setId(id);
                    return ResponseEntity.ok(libroService.createLibro(libro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE con ID libro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }
}
