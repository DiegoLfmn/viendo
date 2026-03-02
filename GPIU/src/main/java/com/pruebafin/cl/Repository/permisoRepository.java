package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.permisoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface permisoRepository extends JpaRepository<permisoEntity, Long> {

}
