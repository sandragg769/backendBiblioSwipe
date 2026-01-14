package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.BibliotecaDTO;
import com.biblioswipe.backend.model.Biblioteca;
import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.service.BibliotecaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/bibliotecas")
@CrossOrigin(origins = "*")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    // GET bibliotecas
    @GetMapping
    public List<Biblioteca> getAllBibliotecas() {
        return bibliotecaService.getAllBibliotecas();
    }

    // GET con ID biblioteca
    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaDTO> getBibliotecaById(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecaService.getBibliotecaDTO(id));
    }

    // POST biblioteca
    @PostMapping
    public Biblioteca createBiblioteca(@RequestBody Biblioteca biblioteca) {
        return bibliotecaService.createBiblioteca(biblioteca);
    }

    // PUT con ID biblioteca
    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> updateBiblioteca(@PathVariable Long id, @RequestBody Biblioteca biblioteca) {
        return ResponseEntity.ok(bibliotecaService.updateBiblioteca(id, biblioteca));
    }

    // POST con ID libro a futuras lecturas
    // Añadir libro a futuras lecturas
    @PostMapping("/{id}/futuras/{libroId}")
    public ResponseEntity<Biblioteca> addLibroAFuturas(@PathVariable Long id, @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.agregarLibroAFuturas(id, libroId));
    }

    // POST con ID libro a leídos
    // Añadir libro a leídos
    @PostMapping("/{id}/leidos/{libroId}")
    public ResponseEntity<Biblioteca> addLibroALeidos(@PathVariable Long id, @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.agregarLibroALeidos(id, libroId));
    }

    // POST con ID libro a recomendados
    // Añadir libro a recomendados
    @PostMapping("/{id}/recomendados/{libroId}")
    public ResponseEntity<BibliotecaDTO> addRecomendado(@PathVariable Long id, @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.agregarLibroARecomendados(id, libroId));
    }

    // GET con ID libros leídos
    // Ver libros leídos
    @PostMapping("/{id}/leidos/{libroId}")
    public ResponseEntity<BibliotecaDTO> addLeido(@PathVariable Long id, @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.agregarLibroALeidos(id, libroId));
    }

    // GET con ID libros recomendados
    //ver libros recomendados
    @GetMapping("/{id}/recomendados")
    public ResponseEntity<Set<Libro>> getLibrosRecomendados(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecaService.getLibrosRecomendados(id));
    }

    // GET con ID libros futuras
    //ver futuras lecturas
    @GetMapping("/{id}/futuras")
    public ResponseEntity<Set<Libro>> getFuturasLecturas(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecaService.getFuturasLecturas(id));
    }

    // Eliminar libro de valda futuras lecturas
    //DELETE con ID de biblioteca futuras lecturas con ID específico de libro
    @DeleteMapping("/{id}/futuras/{libroId}")
    public ResponseEntity<Biblioteca> removeLibroDeFuturas(@PathVariable Long id, @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.removeLibroDeFuturas(id, libroId));
    }

    // Eliminar libro de valda leídos
    //DELETE con ID de biblioteca leídos con ID específico de libro
    @DeleteMapping("/{id}/leidos/{libroId}")
    public ResponseEntity<Biblioteca> removeLibroDeLeidos(@PathVariable Long id, @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.removeLibroDeLeidos(id, libroId));
    }

    // Eliminar libro de valda recomendados
    //DELETE con ID de biblioteca recomendados con ID específico de libro
    @DeleteMapping("/{id}/recomendados/{libroId}")
    public ResponseEntity<Biblioteca> removeLibroDeRecomendados(@PathVariable Long id, @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.removeLibroDeRecomendados(id, libroId));
    }

}
