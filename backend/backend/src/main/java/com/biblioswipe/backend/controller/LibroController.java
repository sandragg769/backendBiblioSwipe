package com.biblioswipe.backend.controller;

import java.util.List;
import com.biblioswipe.backend.dto.LibroCreateDTO;
import com.biblioswipe.backend.dto.LibroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.biblioswipe.backend.service.LibroService;

@RestController
@RequestMapping("/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<LibroDTO>> getAllLibros() {
        return ResponseEntity.ok(libroService.getAllLibros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getLibroById(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.getLibroById(id));
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<LibroDTO>> buscarPorAutor(@PathVariable String autor) {
        return ResponseEntity.ok(libroService.buscarPorAutor(autor));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscar(@RequestParam String query) {
        return ResponseEntity.ok(libroService.buscarPorLibros(query));
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<LibroDTO>> getLibrosByCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.getLibrosByCategoria(id));
    }

    // --- ESTE ES EL MÉTODO CORRECTO (Solo uno) ---
    @PostMapping
    public ResponseEntity<LibroDTO> crearLibro(@RequestBody LibroCreateDTO dto) {
        // Llama al servicio que espera LibroCreateDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crearLibro(dto));
    }
}