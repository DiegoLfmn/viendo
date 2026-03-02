package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "rol")
public class rolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol",length = 100)
    private Long id_rol;

    @Column(name = "nombre_rol",length = 25)
    private String nombre_rol;

    @Column(name = "descripcion_rol",length = 1024)
    private String descripcion_rol;


    ///  1 ROL TIENE MUCHOS PERMISOS
    /// 1 PERMISO ESTA ASIGNADO A VARIOS ROLES

    @ManyToMany
    @JoinTable(
            name = "rol_permisos",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "permisos_id")
    )
    private Set<permisoEntity> permisosRol = new HashSet<>();


    /// 1 ROL puede estar asignado a varios USUARIOS
    /// 1 USUARIO puede tener varios ROLES
    ///
    @ManyToMany(mappedBy = "roles")
    private Set<usuarioEntity> usuariosAsignados = new HashSet<>();


}
