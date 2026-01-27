// Archivo: com.biblioswipe.backend.model.Biblioteca.java
package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Biblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true) // Corregido snake_case
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    // --- SECCIÓN: FUTURAS LECTURAS ---
    @ManyToMany(fetch = FetchType.EAGER) // Cambiado a EAGER para asegurar carga en perfil
    @JoinTable(
        name = "biblioteca_libros_futuras_lecturas", // Unificado con SQL
        joinColumns = @JoinColumn(name = "biblioteca_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    private Set<Libro> librosFuturasLecturas = new HashSet<>();

    // --- SECCIÓN: LEÍDOS ---
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "biblioteca_libros_leidos",
        joinColumns = @JoinColumn(name = "biblioteca_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    private Set<Libro> librosLeidos = new HashSet<>();

    // --- SECCIÓN: RECOMENDADOS ---
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "biblioteca_libros_recomendados",
        joinColumns = @JoinColumn(name = "biblioteca_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    private Set<Libro> librosRecomendados = new HashSet<>();

    public Biblioteca() {}
    public Biblioteca(Usuario usuario) { this.usuario = usuario; }
}