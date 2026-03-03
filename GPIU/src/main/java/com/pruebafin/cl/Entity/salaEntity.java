package com.pruebafin.cl.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name ="sala")
public class salaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala", length = 100)
    private Long id_sala;

    @Column(name = "codigo_sala", length = 200)
    private String codigoSala;

    @Column(name = "piso_sala", length = 20)
    private Integer  pisoSala;

    @Column(name = "capacidad_sala", length = 10)
    private Integer capacidadSala;

    @Column(name = "latitud_sala")
    private Double latitudSala;

    @Column(name = "longitud")
    private Double longitudSala;


    /// EVENTO
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private eventoEntity evento;


    /// EDIFICIO
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edificio_id")
    private edificioEntity edificio;

    /// PUNTOINTERES
    @JsonIgnore
    @OneToOne(mappedBy = "salaInteres")
    private puntointeresEntity puntointeres;

    /// PIU
    @JsonIgnore
    @OneToMany(mappedBy = "salaConPiu")
    private Set<piuEntity> pius = new HashSet<>();


}
