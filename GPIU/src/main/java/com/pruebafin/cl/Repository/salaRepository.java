package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.salaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface salaRepository extends JpaRepository<salaEntity,Long> {
//    List<salaEntity> findByCodigo(String codigo);
//
//    List<salaEntity> findByPiso(int piso);
//
//    List<salaEntity> findByCapacidad(String nombre);
}
