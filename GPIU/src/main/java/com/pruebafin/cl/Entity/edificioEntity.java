package com.pruebafin.cl.Entity;
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

    @Column(name = "nombre_edificio", length = 20)
    private String nombre_edificio;

    @Column(name = "codigo_edificio", length = 100)
    private String codigo_edificio;


    /// SALA
    @OneToMany(mappedBy = "edificio")
    private Set<salaEntity> salas = new HashSet<>();


    /// CAMPUS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id")
    private campusEntity campus;
}
