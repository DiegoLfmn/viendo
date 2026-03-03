package com.pruebafin.cl.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name ="piu")
public class piuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piu", length = 100)
    private Long id_piu;
    @Column(name = "codigo_piu", length = 200)
    private String codigoPiu;

    @Column(name = "estado_piu", length = 20)
    private String estadoPiu;

    @Column(name = "fecha_instalacion", length = 10)
    private Date fechaInstalacion;

    @Column(name = "latitud_piu")
    private Double latitudPiu;

    @Column(name = "longitud_piu")
    private Double longitudPiu;

    /// INCIDENCIA PIU
    @JsonIgnore
    @OneToMany(mappedBy = "piuIncidente")
    private List<incidenciapiuEntity> incidenciaspiu = new ArrayList<>();

    /// SOLICITUD DESCONEXION PIU
    @JsonIgnore
    @OneToMany(mappedBy = "piuDesconexion")
    private List<solicituddesconexionpiuEntity> solidesconexion = new ArrayList<>();

    /// BITACORA ACCESO
    @JsonIgnore
    @OneToMany(mappedBy = "piuDeBita")
    private List<bitacoraAccesoEntity> bitacora_acceso;

    /// RUTA
    @JsonIgnore
    @OneToMany(mappedBy = "piuConRuta")
    private Set<rutaEntity> ruta = new HashSet<>();

    /// SALA
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private salaEntity salaConPiu;
}