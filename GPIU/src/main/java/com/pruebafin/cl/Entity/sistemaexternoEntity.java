package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "sistema_extenero")
public class sistemaexternoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sistema", length = 100)
    private Long id_sistema;

    @Column(name = "nombre_sistext", length = 20)
    private String nombre_sistext;

    @Column(name = "tipo_sistext", length = 30)
    private String tipo_sistext;

    @Column(name = "url_servicio", length = 30)
    private String url_servicio;

    /// CONTENIDO
    /// 1 SISTEMA DIRECCIONA DE MUCHOS CONTENIDOS

    @OneToMany(mappedBy = "sistemaExternoC")
    private List<contenidoEntity> contenidoWSistema = new ArrayList<>();
}
