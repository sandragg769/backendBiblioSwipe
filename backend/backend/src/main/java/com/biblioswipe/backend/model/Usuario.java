package com.biblioswipe.backend.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Getter 
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "usuarioId"
)
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include 
    private Long usuarioId;


    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

   @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Evita que al imprimir el usuario se intente imprimir el perfil
    private Perfil perfil;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Biblioteca biblioteca;

    // relación N:M con categorias
    // categorías favoritas (filtro)
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Categoria> categorias = new HashSet<>();

   @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Usuario> usuariosFavoritos = new HashSet<>();

    @ManyToMany(mappedBy = "usuariosFavoritos", fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Usuario> esFavoritoDe = new HashSet<>();

    // constructores
    public Usuario() {
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }
}