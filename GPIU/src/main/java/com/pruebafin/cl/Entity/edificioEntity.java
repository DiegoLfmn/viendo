package com.pruebafin.cl.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name ="edificio")
public class edificioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_edificio", length = 100)
    private Long id_edificio;

    @Column(name = "nombre_edificio", length = 100)
    private String nombreEdificio;

    @Column(name = "codigo_edificio", length = 100)
    private String codigo_edificio;

    @Column(name = "latitud_edificio")
    private Double latitudEdificio;

    @Column(name = "longitud_edificio")
    private Double longitudEdificio;

    /// SALA
    @JsonIgnore
    @OneToMany(mappedBy = "edificio")
    private Set<salaEntity> salas = new HashSet<>();


    /// CAMPUS
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id")
    private campusEntity campus;
}
