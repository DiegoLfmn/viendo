package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "indicencia_piu")
public class incidenciapiuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia",length = 100)
    private Long id_incidencia;

    @Column(name = "fecha_reporte", length = 20)
    private LocalDateTime fecha_reporte;

    @Column(name = "descripcion_incidencia", length = 1024)
    private String descripcion_incidencia;

    @Column(name = "estado_incidencia", length = 20)
    private String estado_incidencia;

    @Column(name = "prioridad", length = 20)
    private String prioridad;

    ///  PIU
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piu_id")
    private piuEntity piuIncidente;


    ///  USUARIO
    /// ver bien ya que 1 usuario puede tener muchas incidencias
    /// pero 1 incidencia es hecha por 1 incidencia

    @ManyToMany(mappedBy = "incidenciasPiu")
    private Set<usuarioEntity> usuariosIncidentes = new HashSet<>();


}
