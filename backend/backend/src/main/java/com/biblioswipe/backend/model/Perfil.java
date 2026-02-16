package com.biblioswipe.backend.model;

import java.time.LocalDate;
import java.time.Period;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perfil_id;

    private String nombre;
    private String apellidos;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private String ciudad;
    private String fotoPerfil;

    // relación usuario 1:1
    // TENÍA EL FETCH EAGER ANTES DE CAMBIAR POR EXCEPTION DEL BACK DE SANDRA
    @OneToOne
    @JoinColumn(name = "usuarioId", nullable = false, unique = true)
    @EqualsAndHashCode.Exclude
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
    @Transient // Esto indica que NO se crea una columna en la base de datos
    public int getEdad() {
        if (this.fechaNacimiento == null) return 0;
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }
}
