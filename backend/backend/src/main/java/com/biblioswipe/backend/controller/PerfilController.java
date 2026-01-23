package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.PerfilDTO;
import com.biblioswipe.backend.dto.PerfilUpdateDTO;
import com.biblioswipe.backend.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
@CrossOrigin(origins = "*")
public class PerfilController {

    private final PerfilService perfilService;

    @Autowired
    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    // GET con ID perfiles
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<PerfilDTO> getPerfilByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(perfilService.getPerfilByUsuario(usuarioId));
    }

    // GET con ciudad perfiles
    // buscar perfil por ciudad
    // NO SE NECESITA, LO DEJO POR SI ACASO
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<PerfilDTO>> getPerfilesByCiudad(@PathVariable String ciudad) {
        List<PerfilDTO> perfiles = perfilService.findByCiudad(ciudad);
        return ResponseEntity.ok(perfiles);
    }

    // PUT con ID perfil
    @PutMapping("/usuario/{usuarioId}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(
            @PathVariable Long usuarioId,
            @RequestBody PerfilUpdateDTO dto) {

        return ResponseEntity.ok(perfilService.actualizarPerfil(usuarioId, dto));
    }
}
