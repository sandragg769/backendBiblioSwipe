package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.model.Usuario;
import com.biblioswipe.backend.repository.UsuarioRepository;
import com.biblioswipe.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//controller es para exponer de los endpoints 
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET
    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // GET con ID
    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET con Email
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
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }

    // PUT con ID
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
    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // POST con ID categoría a un usuario
    // Añadir categoría a un usuario
    @PostMapping("/{id}/categorias/{categoriaId}")
    public ResponseEntity<Usuario> addCategoriaToUsuario(@PathVariable Long id, @PathVariable Long categoriaId) {
        Usuario updated = usuarioService.agregarCategoria(id, categoriaId);
        return ResponseEntity.ok(updated);
    }

    // DELETE con ID categoría a un usuario
    // Quitar categoría
    @DeleteMapping("/{id}/categorias/{categoriaId}")
    public ResponseEntity<Usuario> removeCategoriaFromUsuario(@PathVariable Long id, @PathVariable Long categoriaId) {
        Usuario updated = usuarioService.eliminarCategoria(id, categoriaId);
        return ResponseEntity.ok(updated);
    }

    // POST con ID de usuario los favoritos
    // Agregar usuario a favoritos
    @PostMapping("/{id}/favoritos/{favoritoId}")
    public ResponseEntity<Usuario> addUsuarioFavorito(@PathVariable Long id, @PathVariable Long favoritoId) {
        Usuario updated = usuarioService.agregarFavorito(id, favoritoId);
        return ResponseEntity.ok(updated);
    }

    // GET con ID de usuario los favoritos
    // Ver favoritos de un usuario
    @GetMapping("/{id}/favoritos")
    public ResponseEntity<Set<Usuario>> getUsuariosFavoritos(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getFavoritos(id));
    }

    //REGISTRO
    @PostMapping("/register")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            String mensaje = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        boolean success = usuarioService.login(usuario.getEmail(), usuario.getPassword());
        if (success) {
            return ResponseEntity.ok("Login correcto");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrectos");
        }
    }
}