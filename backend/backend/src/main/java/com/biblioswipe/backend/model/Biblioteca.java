package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Getter 
@Setter
@ToString
//  CLAVE: Usamos solo el ID para evitar que Hibernate entre en bucle infinito al calcular el hash
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "biblioteca")
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @JsonIgnore // Evita que el JSON se vuelva infinito al serializar el usuario de vuelta
    @ToString.Exclude // Evita que el log de la consola se bloquee por recursión
    private Usuario usuario;

    // --- SECCIÓN: FUTURAS LECTURAS ---
    // Usamos EAGER para que cuando Android pida la biblioteca, los libros vengan incluidos
    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
        name = "biblioteca_libros_futuras_lecturas",
        joinColumns = @JoinColumn(name = "biblioteca_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    @ToString.Exclude
    private Set<Libro> librosFuturasLecturas = new HashSet<>();

    // --- SECCIÓN: LEÍDOS ---
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "biblioteca_libros_leidos",
        joinColumns = @JoinColumn(name = "biblioteca_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    @ToString.Exclude
    private Set<Libro> librosLeidos = new HashSet<>();

    // --- SECCIÓN: RECOMENDADOS ---
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "biblioteca_libros_recomendados",
        joinColumns = @JoinColumn(name = "biblioteca_id"),
        inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    @ToString.Exclude
    private Set<Libro> librosRecomendados = new HashSet<>();

    // Constructores
    public Biblioteca() {}

    public Biblioteca(Usuario usuario) {
        this.usuario = usuario;
    }
}