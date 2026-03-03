package com.pruebafin.cl.Repository;


import com.pruebafin.cl.Entity.rolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface rolRepository extends JpaRepository<rolEntity,Long> {
    List<rolEntity> findByNombreRol(String nombreRol);


}
