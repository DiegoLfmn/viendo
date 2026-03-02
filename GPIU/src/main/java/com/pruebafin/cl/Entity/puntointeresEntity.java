package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name ="punto_interes")
public class puntointeresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_punto", length = 100)
    private Long id_punto;

    @Column(name = "nombre_interes", length = 100)
    private String nombre_interes;

    @Column(name = "tipo_interes", length = 20)
    private String tipo_interes;

    @Column(name = "coordenada_x", length = 20)
    private Float coordenada_x;

    @Column(name = "coordenada_y", length = 20)
    private  Float coordenada_y;

    /// SALA
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private salaEntity salaInteres;


    /// RUTA
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ruta_punto",
            joinColumns = @JoinColumn(name = "puntointeres_id"),
            inverseJoinColumns = @JoinColumn(name = "ruta_id")
    )
    private Set<rutaEntity> rutaInteres = new HashSet<>();


    /// CAMPUS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id")
    private campusEntity campusInteres;


}
