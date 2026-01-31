package com.biblioswipe.backend.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include 
    private Long perfil_id;

    private String nombre;
    private String apellidos;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private String ciudad;
    private String fotoPerfil;

    @OneToOne
    @JoinColumn(name = "usuarioId", nullable = false, unique = true)
    @ToString.Exclude 
    private Usuario usuario;

    // constructores
    public Perfil() {
    }

    public Perfil(String nombre, String apellidos, LocalDate fechaNacimiento, String ciudad, String fotoPerfil) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
        this.fotoPerfil = fotoPerfil;
    }
}
