package com.biblioswipe.backend.controller;

import com.biblioswipe.backend.dto.PerfilConUsuarioDTO;
import com.biblioswipe.backend.mapper.PerfilMapper;
import com.biblioswipe.backend.model.Perfil;
import com.biblioswipe.backend.model.Usuario;
import com.biblioswipe.backend.service.PerfilService;
import com.biblioswipe.backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
@CrossOrigin(origins = "*")
public class PerfilController {

    private final PerfilService perfilService;
    private final UsuarioService usuarioService;


    public PerfilController(PerfilService perfilService, UsuarioService usuarioService) {
        this.perfilService = perfilService;
        this.usuarioService = usuarioService;
    }

    // GET perfiles
    @GetMapping
    public List<PerfilConUsuarioDTO> getAllPerfiles() {
        return perfilService.getAllPerfiles()
                .stream()
                .map(PerfilMapper::toDTO)
                .toList();
    }

    // GET con ID perfiles
    @GetMapping("/{id}")
    public ResponseEntity<PerfilConUsuarioDTO> getPerfilById(@PathVariable Long id) {
        return perfilService.getPerfilById(id)
                .map(PerfilMapper::toDTO)
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
    @PostMapping("/usuarios/{idUsuario}/perfil")
    public ResponseEntity<?> crearPerfil(
            @PathVariable Long idUsuario,
            @RequestBody Perfil perfil) {

        Usuario usuario = usuarioService.getUsuarioById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getPerfil() != null) {
            return ResponseEntity
                    .badRequest()
                    .body("Este usuario ya tiene un perfil");
        }

        perfil.setUsuario(usuario);
        usuario.setPerfil(perfil);

        return ResponseEntity.ok(perfilService.createPerfil(perfil));
    }


    // PUT con ID perfil
    @PutMapping("/{id}")
    public ResponseEntity<Perfil> updatePerfil(
            @PathVariable Long id,
            @RequestBody Perfil perfilActualizado) {

        return perfilService.getPerfilById(id)
                .map(perfilExistente -> {

                    // 🔒 mantener usuario (CLAVE)
                    perfilActualizado.setUsuario(perfilExistente.getUsuario());

                    // 🔒 mantener ID
                    perfilActualizado.setPerfil_id(id);

                    return ResponseEntity.ok(
                            perfilService.createPerfil(perfilActualizado)
                    );
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
