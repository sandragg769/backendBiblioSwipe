package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.PerfilUpdateDTO;
import com.biblioswipe.backend.service.PerfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
@CrossOrigin(origins = "*")
public class PerfilController {

    private final PerfilService perfilService;


    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    // GET perfiles
    // NO SE NECESITA, LO DEJO POR SI ACASO
    @GetMapping
    public List<PerfilDTO> getAllPerfiles() {
        return perfilService.getAllPerfiles();
    }

    // GET con ID perfiles
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPerfilById(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.getPerfilById(id));
    }

    // GET con ciudad perfiles
    // buscar perfil por ciudad
    // NO SE NECESITA, LO DEJO POR SI ACASO
    @GetMapping("/ciudad/{ciudad}")
    public List<PerfilDTO> getPerfilesByCiudad(@PathVariable String ciudad) {
        return perfilService.findByCiudad(ciudad);
    }

    // PUT con ID perfil
    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> updatePerfil(
            @PathVariable Long id,
            @RequestBody PerfilUpdateDTO dto) {

        return ResponseEntity.ok(
                perfilService.updatePerfil(id, dto)
        );
    }

    // no delete, no tenemos esas implementaciones todavía
}
