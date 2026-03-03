package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.permisoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface permisoRepository extends JpaRepository<permisoEntity, Long> {
    List<permisoEntity> findByCodigo(String codigo);
}
