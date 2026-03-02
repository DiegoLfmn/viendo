package com.pruebafin.cl.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name ="campus")
public class campusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campus", length = 100)
    private Long id_campus;

    @Column(name = "nombre_campus", length = 100)
    private String nombre_campus;


    /// EDIFICIO
    @OneToMany(mappedBy = "campus")
    private Set<edificioEntity> edificios = new HashSet<>();

    /// PUNTO INTERES
    @OneToMany(mappedBy = "campusInteres")
    private Set<puntointeresEntity> puntosEnCampus = new HashSet<>();

}
