package com.biblioswipe.backend.controller;

import java.util.List;

import com.biblioswipe.backend.dto.LibroCreateDTO;
import com.biblioswipe.backend.dto.LibroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<LibroDTO>> getAllLibros() {
        return ResponseEntity.ok(libroService.getAllLibros());
    }

    // GET con ID libro
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getLibroById(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.getLibroById(id));
    }

    // GET con autor libro
    // Buscar por autor
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<LibroDTO>> buscarPorAutor(@PathVariable String autor) {
        return ResponseEntity.ok(libroService.buscarPorAutor(autor));
    }

    // GET con título libro
    // Buscar por título
    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscar(@RequestParam String query) {
        // Este usa el metodo que busca tanto por título como por autor
        return ResponseEntity.ok(libroService.buscarPorLibros(query));
    }

    // GET con categoria libro
    // Buscar por categoría
    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<LibroDTO>> getLibrosByCategoria(@PathVariable Long id) {
        // Usamos el ID de la categoría (Long) porque es lo que envía el front (1, 2, 3...)
        return ResponseEntity.ok(libroService.getLibrosByCategoria(id));
    }

    // POST libro
    @PostMapping
    public ResponseEntity<LibroDTO> crearLibro(@RequestBody LibroCreateDTO dto) {
        // Usamos HttpStatus.CREATED (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crearLibro(dto));
    }
}
