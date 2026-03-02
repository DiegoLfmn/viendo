package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bitacora_acceso")
public class bitacoraAccesoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro", length = 100)
    private Long id_registro;

    @Column(name = "fecha_hora", length = 20)
    private String fecha_hora;

    @Column(name = "tipo_operacion", length = 100)
    private String tipo_operacion;

    @Column(name = "detalle_bitacora", length = 1024)
    private String detalle_bitacora;


    /// USUARIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private usuarioEntity usuario;

    /// CONTENIDO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contenido_id")
    private contenidoEntity contenido;

    /// RUTA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_id")
    private rutaEntity ruta;

    /// PIU
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piu_id")
    private piuEntity piuDeBita;
}

