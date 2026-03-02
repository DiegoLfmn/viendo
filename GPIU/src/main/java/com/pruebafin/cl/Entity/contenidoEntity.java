package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "contenido")
@Inheritance(strategy = InheritanceType.JOINED)
public class contenidoEntity {
    @Id
    @GeneratedValue
    @Column(name = "id_contenido", length = 100)
    private Long id_contenido;

    @Column(name = "titulo",length = 20)
    private String titulo;

    @Column(name = "texto", length = 1024)
    private String texto;

    @Column(name = "tipo", length = 10)
    private String tipo;

    @Column(name = "estado", length = 15)
    private String estado;

    @Column(name = "fecha_creacion", length = 10, nullable = false)
    private Date fecha_creacion;

    @Column(name = "fecha_publicacion", length = 10, nullable = false)
    private Date fecha_publicacion;

    @Column(name = "fecha_modificacion", length = 10, nullable = false)
    private Date fecha_modificacion;

    @Column(name = "enlace_externo", length = 50)
    private String enlace_externo;

    ///@Column(name = "unidad_responsable", length = 20)
    ///private  unidad_responsable;
    /// falta ver los id foraneos


    // Crea contenido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private usuarioEntity autor;

    // consulta contenido
    @ManyToMany(mappedBy = "contenido_consulta")
    private Set<usuarioEntity> usuariosLeen = new HashSet<>();


    ///  BITACORA
    /// 1 contenido puede tener 1 bitacora de acceso
    /// 1 bitacora tiene 1 contenido

    @OneToMany(mappedBy = "contenido")
    private List<bitacoraAccesoEntity> registroBitacora  ;

    ///  verificar que este bien hecho, no entiendo bien como funciona este apartado


    ///  UNIDAD ORGANIZACIONAL
    /// MUCHO A 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_organizacional_id")
    private unidadorganizacionalEntity unidadResponsable;

    /// SISTEMA EXTERNO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sistema_externo_id")
    private sistemaexternoEntity sistemaExternoC;

    /// EVENTO

    // al ser herencia tiene otro tipo de comportamiento



}
