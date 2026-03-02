package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "solicitud_desconexion_piu")
public class solicituddesconexionpiuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud", length = 100)
    private Long id_solicitud;

    @Column(name = "fecha_solicitud", length = 20)
    private LocalDateTime fecha_solicitud;

    @Column(name = "motivo", length = 1024)
    private String motivo;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "fecha_resolucion", length = 400)
    private LocalDateTime fecha_resolucion;

    ///  PIU
    ///  MUCHOS A 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piu_id")
    private piuEntity piuDesconexion;



}
