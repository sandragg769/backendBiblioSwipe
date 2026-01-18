package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.*;
import com.biblioswipe.backend.model.Usuario;
import com.biblioswipe.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//controller es para exponer de los endpoints
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST
    // Crear nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(
            @RequestBody UsuarioRegisterDTO dto
    ) {
        return ResponseEntity.ok(usuarioService.register(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuariobyId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUsuarioDTO(id));
    }

    // POST con ID de usuario los favoritos
    // agregar usuario a favoritos
    @PostMapping("/{id}/favoritos/{favoritoId}")
    public ResponseEntity<Void> addFavorito(
            @PathVariable Long id,
            @PathVariable Long favoritoId
    ) {
        usuarioService.agregarFavorito(id, favoritoId);
        return ResponseEntity.ok().build();
    }

    // GET con ID de usuario los favoritos
    // Ver favoritos de un usuario
    @GetMapping("/{id}/favoritos")
    public ResponseEntity<List<UsuarioDTO>> getFavoritos(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(usuarioService.getFavoritos(id));
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(
            @RequestBody LoginRequestDTO dto
    ) {
        return ResponseEntity.ok(
                usuarioService.login(dto.getEmail(), dto.getPassword())
        );
    }

    @GetMapping("/match/categoria/{nombre}")
    public ResponseEntity<List<UsuarioDTO>> matchPorCategoria(
            @PathVariable String nombre
    ) {
        return ResponseEntity.ok(
                usuarioService.buscarUsuariosPorCategoria(nombre)
        );
    }

    // otros getters
    @GetMapping("/{id}/perfil")
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getPerfil(id));
    }

    @GetMapping("/{id}/biblioteca")
    public ResponseEntity<BibliotecaDTO> getBiblioteca(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getBiblioteca(id));
    }

}