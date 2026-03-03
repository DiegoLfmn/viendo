package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.salaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface salaRepository extends JpaRepository<salaEntity,Long> {
    List<salaEntity> findByCodigoSala(String codigo);

    List<salaEntity> findByPisoSala(Integer piso);

    List<salaEntity> findByCapacidadSala(Integer capacidad);
}
