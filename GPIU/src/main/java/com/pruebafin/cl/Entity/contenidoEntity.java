package com.pruebafin.cl.Entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class contenidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contenido", length = 100)
    private Long id_contenido;

<<<<<<< HEAD
    @Column(name = "titulo",length = 200)
=======
    // Expone "id" en el JSON apuntando a id_contenido
    @JsonGetter("id")
    public Long getId() {
        return this.id_contenido;
    }

    @Column(name = "titulo", length = 200)
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
    private String titulo;

    // Campo principal de texto/cuerpo del contenido (columna BD: texto)
    @Column(name = "texto", length = 2000)
    private String texto;

    // Alias para el frontend (frontend usa "cuerpo")
    @JsonGetter("cuerpo")
    public String getCuerpo() {
        return this.texto;
    }

    // Permite recibir "cuerpo" del frontend y guardarlo en "texto"
    @JsonSetter("cuerpo")
    public void setCuerpo(String cuerpo) {
        this.texto = cuerpo;
    }

    @Column(name = "tipo", length = 20)
    private String tipo;

    @Column(name = "estado", length = 20)
    private String estado;

<<<<<<< HEAD
    @Column(name = "fecha_creacion", length = 10, nullable = true)
    private Date fecha_creacion;

    @Column(name = "fecha_publicacion", length = 10, nullable = true)
    private Date fecha_publicacion;

    @Column(name = "fecha_modificacion", length = 10, nullable = true)
=======
    // Devuelve siempre el estado en minúsculas al JSON
    @JsonGetter("estado")
    public String getEstadoNormalizado() {
        return this.estado != null ? this.estado.toLowerCase() : null;
    }

    @Column(name = "fecha_creacion", nullable = true)
    private Date fecha_creacion;

    // Expone fecha_creacion como fechaCreacion para el frontend
    @JsonGetter("fechaCreacion")
    public String getFechaCreacionStr() {
        if (this.fecha_creacion == null) return null;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.fecha_creacion);
    }

    @Column(name = "fecha_publicacion", nullable = true)
    private Date fecha_publicacion;

    @Column(name = "fecha_modificacion", nullable = true)
>>>>>>> b787786feb3dc4e84859a84bdb255fa2b5b7745e
    private Date fecha_modificacion;

    @Column(name = "enlace_externo", length = 50)
    private String enlace_externo;

    // Nombre del autor como string simple (lo que envía el frontend)
    @Column(name = "autor_nombre", length = 200)
    private String autorNombre;

    // Permite recibir "autor" del frontend como string y guardarlo en autorNombre
    @JsonSetter("autor")
    public void setAutorFromString(String autor) {
        this.autorNombre = autor;
    }

    // Expone "autor" en el JSON como el nombre del autor
    @JsonGetter("autor")
    public String getAutorStr() {
        return this.autorNombre;
    }

    @Column(name = "categoria", length = 100)
    private String categoria;

    @Column(name = "fecha_vigencia_inicio", nullable = true)
    private Date fechaVigenciaInicio;

    @Column(name = "fecha_vigencia_fin", nullable = true)
    private Date fechaVigenciaFin;

    // La relación ManyToOne con usuarioEntity se ignora completamente en JSON
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private usuarioEntity autorEntidad;

    // consulta contenido
    @JsonIgnore
    @ManyToMany(mappedBy = "contenido_consulta")
    private Set<usuarioEntity> usuariosLeen = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "contenido")
    private List<bitacoraAccesoEntity> registroBitacora;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_organizacional_id")
    private unidadorganizacionalEntity unidadResponsable;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sistema_externo_id")
    private sistemaexternoEntity sistemaExternoC;
}