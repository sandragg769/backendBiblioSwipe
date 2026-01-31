package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.*;
import com.biblioswipe.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //  NUEVO ENDPOINT PARA LA PRINCIPAL
    // Este método devuelve solo usuarios que NO son favoritos del usuario actual
    @GetMapping("/explorar/{id}")
    public ResponseEntity<List<UsuarioDTO>> getCandidatos(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerCandidatosExploracion(id));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.agregarUsuario(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUsuario(id));
    }

    @PostMapping("/{id}/favoritos/{favoritoId}")
    public ResponseEntity<Void> addFavorito(@PathVariable Long id, @PathVariable Long favoritoId) {
        usuarioService.agregarFavorito(id, favoritoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.login(dto));
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getPerfil(id));
    }

    @GetMapping("/{id}/biblioteca")
    public ResponseEntity<BibliotecaDTO> getBiblioteca(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getBiblioteca(id));
    }

    @GetMapping("/{id}/favoritos")
    public ResponseEntity<List<UsuarioSwipeDTO>> getFavoritos(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getFavoritosParaSwipe(id));
    }

    // Mantenemos este solo para propósitos de administración
    @GetMapping("")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
    
}