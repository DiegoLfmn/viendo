package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "unidad_organizacional")
public class unidadorganizacionalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad", length = 100)
    private Long id_unidad;

    @Column(name = "nombre_unidad", length = 100)
    private String nombre_unidad;

    @Column(name = "tipo_unidad",length = 100)
    private String tipo_unidad;


    /// CONTENIDO
    @OneToMany(mappedBy = "unidadResponsable")
    private List<contenidoEntity> contenidoManejado = new ArrayList<>();




}
