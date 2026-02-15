package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.*;
import com.biblioswipe.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
            @PathVariable Long favoritoId) {
        usuarioService.agregarFavorito(id, favoritoId);
        return ResponseEntity.noContent().build(); // 204 No Content es estándar para void
    }

    @DeleteMapping("/{id}/favoritos/{favoritoId}") // 🎯 Asegurarse de que sea DeleteMapping
    public ResponseEntity<Void> eliminarFavorito(
            @PathVariable Long id,
            @PathVariable Long favoritoId) {
        usuarioService.eliminarFavorito(id, favoritoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload-foto")
    public ResponseEntity<String> uploadFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // 1. Crear la carpeta si no existe
            Path directory = Paths.get("uploads");
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // 2. Nombre de archivo único
            String filename = "perfil_" + id + "_" + System.currentTimeMillis() + ".jpg";
            Path path = directory.resolve(filename);

            // 3. Guardar el archivo físico
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // 4. USAR TU SERVICE CORREGIDO 🎯
            usuarioService.actualizarFoto(id, filename);

            return ResponseEntity.ok(filename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        }
    }

    /**
     * // GET con ID de usuario los favoritos
     * // Ver favoritos de un usuario
     * @GetMapping("/{id}/favoritos")
     * public ResponseEntity<List<UsuarioDTO>> getFavoritos(@PathVariable Long id) {
     * return ResponseEntity.ok(usuarioService.getFavoritos(id));
     * }
     * 
     * @param dto
     * @return
     */

    // LOGIN
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

    // Añadir en UsuarioController.java
    @GetMapping("")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}/favoritos")
    public ResponseEntity<List<UsuarioSwipeDTO>> getFavoritos(@PathVariable Long id) {

        List<UsuarioSwipeDTO> favoritos = usuarioService.getFavoritosParaSwipe(id);
        return ResponseEntity.ok(favoritos);
    }

    /**
     * @GetMapping("/{id}/favoritos/notificaciones")
     * public ResponseEntity<List<NotificacionesFavoritosDTO>>
     * getNotificaciones(@PathVariable Long id) {
     * // Busca cambios de biblioteca de los 'favoritoId' vinculados a este 'id'
     * return
     * ResponseEntity.ok(usuarioService.obtenerNotificacionesDeFavoritos(id));
     * }
     */
}