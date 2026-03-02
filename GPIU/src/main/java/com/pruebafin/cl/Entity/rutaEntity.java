package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name ="ruta")
public class rutaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta", length =100 )
    private Long id_ruta;

    @Column(name = "distancia_ruta", length = 9999)
    private Float distancia_ruta;

    @Column(name = "tiempo_estimado", length = 10)
    private Float tiempo_estimado;



    /// PUNTO INTERES
    @ManyToMany(mappedBy = "rutaInteres")
    private Set<puntointeresEntity> puntosdeinteres = new HashSet<>();

    /// PIU
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piu_id")
    private piuEntity piuConRuta;

    /// BITACORA
    @OneToMany(mappedBy = "ruta")
    private List<bitacoraAccesoEntity> bitacora;


}
