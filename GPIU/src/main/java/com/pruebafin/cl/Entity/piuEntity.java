package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name ="piu")
public class piuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_piu" , length = 100)
    private Long id_piu;
    @Column(name ="codigo_piu" , length = 200)
    private String codigo;

    @Column(name = "estado_piu", length = 20)
    private  String estado;

    @Column(name = "fecha_instalacion", length = 10)
    private Date fecha_instalacion;

    /// INCIDENCIA PIU
    @OneToMany(mappedBy = "piuIncidente")
    private List<incidenciapiuEntity> incidenciaspiu = new ArrayList<>();


    /// SOLICITUD DESCONEXION PIU
    @OneToMany(mappedBy = "piuDesconexion")
    private List<solicituddesconexionpiuEntity> solidesconexion = new ArrayList<>();

    /// BITACORA ACCESO
    @OneToMany(mappedBy = "piuDeBita")
    private List<bitacoraAccesoEntity> bitacora_acceso;



    /// RUTA
    @OneToMany(mappedBy = "piuConRuta")
    private Set<rutaEntity> ruta = new HashSet<>();

    /// SALA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private salaEntity salaConPiu;



}
