package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.*;
import com.biblioswipe.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST
    // Crear nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioRegisterDTO dto) {
        // Usamos CREATED (201) porque estamos creando un recurso nuevo
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.agregarUsuario(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUsuario(id));
    }

    // POST con ID de usuario los favoritos
    // agregar usuario a favoritos
    @PostMapping("/{id}/favoritos/{favoritoId}")
    public ResponseEntity<Void> addFavorito(
            @PathVariable Long id,
            @PathVariable Long favoritoId
    ) {
        usuarioService.agregarFavorito(id, favoritoId);
        return ResponseEntity.noContent().build(); // 204 No Content es estándar para void
    }

    // GET con ID de usuario los favoritos
    // Ver favoritos de un usuario
    @GetMapping("/{id}/favoritos")
    public ResponseEntity<List<UsuarioDTO>> getFavoritos(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getFavoritos(id));
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginRequestDTO dto) {
        // Ahora pasamos el objeto DTO completo al service
        return ResponseEntity.ok(usuarioService.login(dto));
    }

    @GetMapping("/match/categoria/{nombre}")
    public ResponseEntity<List<UsuarioDTO>> matchPorCategoria(@PathVariable String nombre) {
        return ResponseEntity.ok(usuarioService.buscarUsuariosPorCategoria(nombre));
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