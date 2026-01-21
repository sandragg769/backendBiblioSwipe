package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.CategoriaDTO;
import com.biblioswipe.backend.model.Categoria;
import com.biblioswipe.backend.model.Libro;
import com.biblioswipe.backend.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // GET categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.getAllCategorias());
    }

    // GET con ID categorías
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.getCategoriaById(id));
    }

    // GET con nombre categoría
    // Buscar por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDTO> getCategoriaByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(categoriaService.getCategoriaByNombre(nombre));
    }
}
