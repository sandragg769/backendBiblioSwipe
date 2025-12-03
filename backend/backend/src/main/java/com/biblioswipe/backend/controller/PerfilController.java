package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.model.Perfil;
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
    @GetMapping
    public List<Perfil> getAllPerfiles() {
        return perfilService.getAllPerfiles();
    }

    // GET con ID perfiles
    @GetMapping("/{id}")
    public ResponseEntity<Perfil> getPerfilById(@PathVariable Long id) {
        return perfilService.getPerfilById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET con ciudad perfiles
    // Buscar perfil por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public List<Perfil> getPerfilesByCiudad(@PathVariable String ciudad) {
        return perfilService.findByCiudad(ciudad);
    }

    // POST perfil
    @PostMapping
    public Perfil createPerfil(@RequestBody Perfil perfil) {
        return perfilService.createPerfil(perfil);
    }

    // PUT con ID perfil
    @PutMapping("/{id}")
    public ResponseEntity<Perfil> updatePerfil(@PathVariable Long id, @RequestBody Perfil perfil) {
        return perfilService.getPerfilById(id)
                .map(existing -> {
                    perfil.setPerfil_id(id);
                    return ResponseEntity.ok(perfilService.createPerfil(perfil));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE con ID perfil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        perfilService.deletePerfil(id);
        return ResponseEntity.noContent().build();
    }
}
