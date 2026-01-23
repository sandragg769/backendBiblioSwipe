package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.BibliotecaDTO;
import com.biblioswipe.backend.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bibliotecas")
@CrossOrigin(origins = "*")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    @Autowired
    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }


    // GET con ID biblioteca
    // GET biblioteca por ID
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<BibliotecaDTO> getBibliotecaByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(bibliotecaService.getBibliotecaByUsuario(usuarioId));
    }

    // POST con ID libro a futuras lecturas
    // Añadir libro a futuras lecturas
    @PostMapping("/usuario/{usuarioId}/futuras/{libroId}")
    public ResponseEntity<BibliotecaDTO> addFutura(
            @PathVariable Long usuarioId,
            @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.agregarLibroAFuturas(usuarioId, libroId));
    }

    // POST con ID libro a leídos
    // Añadir libro a leídos
    @PostMapping("/usuario/{usuarioId}/leidos/{libroId}")
    public ResponseEntity<BibliotecaDTO> addLeido(
            @PathVariable Long usuarioId,
            @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.agregarLibroALeidos(usuarioId, libroId));
    }

    // POST con ID libro a recomendados
    // Añadir libro a recomendados
    @PostMapping("/usuario/{usuarioId}/recomendados/{libroId}")
    public ResponseEntity<BibliotecaDTO> addRecomendado(
            @PathVariable Long usuarioId,
            @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.agregarLibroARecomendados(usuarioId, libroId));
    }

    /*
    // GET con ID libros leídos
    // Ver libros leídos
    @GetMapping("/{id}/leidos")
    public ResponseEntity<Set<LibroDTO>> getLibrosLeidos(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecaService.getLibrosLeidos(id));
    }

    // GET con ID libros recomendados
    //ver libros recomendados
    @GetMapping("/{id}/recomendados")
    public ResponseEntity<Set<LibroDTO>> getLibrosRecomendados(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecaService.getLibrosRecomendados(id));
    }

    // GET con ID libros futuras
    //ver futuras lecturas
    @GetMapping("/{id}/futuras")
    public ResponseEntity<Set<LibroDTO>> getFuturasLecturas(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecaService.getFuturasLecturas(id));
    }
    */


    // Eliminar libro de valda futuras lecturas
    //DELETE con ID de biblioteca futuras lecturas con ID específico de libro
    @DeleteMapping("/usuario/{usuarioId}/futuras/{libroId}")
    public ResponseEntity<BibliotecaDTO> removeFutura(
            @PathVariable Long usuarioId,
            @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.eliminarLibroDeFuturas(usuarioId, libroId));
    }

    // Eliminar libro de valda leídos
    //DELETE con ID de biblioteca leídos con ID específico de libro
    @DeleteMapping("/usuario/{usuarioId}/leidos/{libroId}")
    public ResponseEntity<BibliotecaDTO> removeLeido(
            @PathVariable Long usuarioId,
            @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.eliminarLibroDeLeidos(usuarioId, libroId));
    }

    // Eliminar libro de valda recomendados
    //DELETE con ID de biblioteca recomendados con ID específico de libro
    @DeleteMapping("/usuario/{usuarioId}/recomendados/{libroId}")
    public ResponseEntity<BibliotecaDTO> removeRecomendado(
            @PathVariable Long usuarioId,
            @PathVariable Long libroId) {
        return ResponseEntity.ok(bibliotecaService.eliminarLibroDeRecomendados(usuarioId, libroId));
    }

}
