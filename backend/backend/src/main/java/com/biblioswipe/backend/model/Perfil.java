package com.biblioswipe.backend.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perfil_id;

    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String ciudad;
    private String fotoPerfil;

    // relación usuario 1:1
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
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
