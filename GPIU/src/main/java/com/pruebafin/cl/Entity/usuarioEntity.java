package com.pruebafin.cl.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String nombreUsuario;

    @Column(name = "correo_usuario", length = 100)
    private String correoUsuario;

    @Column(name = "password_usuario", length = 255)
    private String password_usuario;

    @Column(name = "contrasena_usuario", length = 255)
    private String contrasena_usuario;

    @Column (name = "estado_cuenta" , length = 10)
    private String estado_cuenta;



    // CREADOR
    @JsonIgnore
    @OneToMany(mappedBy = "autor")
    private List<contenidoEntity> contenidos = new ArrayList<>();

    // LECTOR
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_contenido_consulta",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "contenido_id")
    )
    private Set<contenidoEntity> contenido_consulta = new HashSet<>();

    // ROLES
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<rolEntity> roles = new HashSet<>();

    // BITÁCORA
    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<bitacoraAccesoEntity> bitacora = new ArrayList<>();

    // INCIDENCIAS
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_incidencia",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "incidenciapiu_id")
    )
    private List<incidenciapiuEntity> incidenciasPiu = new ArrayList<>();
}
