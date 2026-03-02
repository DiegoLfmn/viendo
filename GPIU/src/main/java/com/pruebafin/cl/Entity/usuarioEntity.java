package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name ="usuario")
public class usuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", length = 100)
    private Long id_usuario;

    @Column(name = "nombre_usuario",length = 80)
    private String nombre_usuario;

    @Column(name = "correo_usuario", length = 20)
    private String correo_usuario;

    @Column (name = "estado_cuenta" , length = 10)
    private String estado_cuenta;



    /// falta ver los id foraneos
    /// 1 usuario puede gestionar crear consultar N contenidos
    /// 1 contenido puede ser gestionado o creado por 1 usuario
    /// 1 contenido puede ser consultado por M usuario
    // CREADOR
    @OneToMany(mappedBy = "autor")
    private List<contenidoEntity> contenidos = new ArrayList<>();
    // LECTOR
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_contenido_consulta",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "contenido_id")
    )
    private Set<contenidoEntity> contenido_consulta = new HashSet<>();
    /// 1 usuario puede tener 1 rol
    /// 1 rol es usado por N usuarios

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<rolEntity> roles = new HashSet<>(); // se evita que se repitan casos como que un usuario tenga 2 veces por ejemplo ESTUDIANTE


    /// 1 usuario puede usar 1 bitacora
    /// 1 bitacora es usado por 1 usuario

    @OneToMany(mappedBy = "usuario")
    private List<bitacoraAccesoEntity> bitacora = new ArrayList<>();





    /// 1 usuario puede tener N incidencias
    /// 1 incidencia puede ser solicitada por M usuarios
    // Un usuario reporta Muchas incidencias
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_incidencia",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "incidenciapiu_id")
    )
    private List<incidenciapiuEntity> incidenciasPiu = new ArrayList<>();

}
