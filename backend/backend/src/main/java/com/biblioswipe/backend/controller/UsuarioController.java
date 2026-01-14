package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.UsuarioDTO;
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

    // GET
    // Obtener todos los usuarios
    @GetMapping
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuariosDTO();
    }

    // GET con ID
    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET con Email
    // NO NECESARIO ??????
    // Buscar usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        return usuarioService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST
    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado correctamente");
    }

    // PUT con ID
    // NO NECESARIO ??????
    // Actualizar usuario completo
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.updateUsuario(id, usuario);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE por ID
    // NO NECESARIO ??????
    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // POST con ID categoría a un usuario
    // Añadir categoría a un usuario
    @PostMapping("/{id}/categorias/{categoriaId}")
    public ResponseEntity<Void> addCategoriaToUsuario(
            @PathVariable Long id,
            @PathVariable Long categoriaId
    ) {
        usuarioService.agregarCategoria(id, categoriaId);
        return ResponseEntity.ok().build();
    }

    // DELETE con ID categoría a un usuario
    // Quitar categoría
    @DeleteMapping("/{id}/categorias/{categoriaId}")
    public ResponseEntity<Void> removeCategoriaFromUsuario(
            @PathVariable Long id,
            @PathVariable Long categoriaId
    ) {
        usuarioService.eliminarCategoria(id, categoriaId);
        return ResponseEntity.noContent().build();
    }

    // POST con ID de usuario los favoritos
    // Agregar usuario a favoritos
    @PostMapping("/{id}/favoritos/{favoritoId}")
    public ResponseEntity<Void> addUsuarioFavorito(
            @PathVariable Long id,
            @PathVariable Long favoritoId
    ) {
        usuarioService.agregarFavorito(id, favoritoId);
        return ResponseEntity.ok().build();
    }

    // GET con ID de usuario los favoritos
    // Ver favoritos de un usuario
    @GetMapping("/{id}/favoritos")
    public List<UsuarioDTO> getUsuariosFavoritos(@PathVariable Long id) {
        return usuarioService.getFavoritosDTO(id);
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        boolean success = usuarioService.login(
                usuario.getEmail(),
                usuario.getPassword()
        );

        if (success) {
            return ResponseEntity.ok("Login correcto");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email o contraseña incorrectos");
        }
    }
}