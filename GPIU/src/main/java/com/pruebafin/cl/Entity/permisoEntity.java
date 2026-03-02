package com.pruebafin.cl.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "permiso")
public class permisoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso", length = 100)
    private Long id_permiso;

    @Column(name = "codigo", length = 20)
    private String codigo;

    @Column(name = "descripcion_permiso",length = 1024)
    private String descripcion_permiso;



    /// 1 PERMISO está puesto para varios ROLES
    /// 1 ROL tiene varios PERMISOS

    @ManyToMany(mappedBy = "permisosRol")
    private Set<rolEntity> rolesAsignados = new HashSet<>();

}
