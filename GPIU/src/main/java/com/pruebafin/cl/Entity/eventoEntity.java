package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.*;

@Data
@Entity
@Table(name = "evento")
public class eventoEntity extends contenidoEntity {

    @Column(name = "fecha", length = 10)
    private Date fecha;

    @Column(name = "hora_inicio", length = 8)
    private Time hora_inicio;

    @Column(name = "hora_fin", length = 8)
    private Time hora_fin;

    @Column(name = "tipo_evento", length = 30)
    private String tipo_evento;

    @Column(name = "organizador", length = 20)
    private String organizador;


    /// SALA
    @OneToMany(mappedBy = "evento")
    private Set<salaEntity> salas =  new HashSet<>();


}
